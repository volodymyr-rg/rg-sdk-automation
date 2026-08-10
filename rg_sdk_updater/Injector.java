package rg_sdk_updater;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Injector {
  private static final String GENERATED_WARNING =
      "WARNING: This content is generated. Do not edit manually.";

  public static void main(String[] args) {
    try {
      System.out.print(inject(args));
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
      System.exit(1);
    }
  }

  static String inject(String[] args) throws IOException {
    if (args.length != 4) {
      throw new InjectorException(
          "Usage: Injector <start-marker> <end-marker> <data.md> <input-source-file>");
    }

    String startMarker = args[0];
    String endMarker = args[1];
    Path dataPath = Path.of(args[2]);
    Path sourcePath = Path.of(args[3]);
    SourceType sourceType = SourceType.fromPath(sourcePath);

    String data = Files.readString(dataPath, StandardCharsets.UTF_8);
    String source = Files.readString(sourcePath, StandardCharsets.UTF_8);
    List<ConstantEntry> entries = parseEntries(data);
    String newline = detectNewline(source);

    MarkerRegion markerRegion = findMarkerRegion(source, startMarker, endMarker);
    String generated = generate(sourceType, entries, markerRegion.indent, newline);

    return source.substring(0, markerRegion.replacementStart)
        + generated
        + source.substring(markerRegion.replacementEnd);
  }

  private static List<ConstantEntry> parseEntries(String data) {
    List<ConstantEntry> entries = new ArrayList<>();
    String[] lines = data.split("\\R", -1);

    String currentName = null;
    List<String> currentDescription = new ArrayList<>();
    for (int i = 0; i < lines.length; i++) {
      String line = stripBom(lines[i]);
      String trimmed = line.trim();
      if (trimmed.startsWith("#")) {
        if (currentName != null) {
          entries.add(new ConstantEntry(currentName, trimBlankLines(currentDescription)));
        }
        currentName = trimmed.replaceFirst("^#+", "").trim();
        if (currentName.isEmpty()) {
          throw new InjectorException("Empty constant name at data line " + (i + 1));
        }
        if (!isValidIdentifier(currentName)) {
          throw new InjectorException("Invalid constant name at data line " + (i + 1) + ": " + currentName);
        }
        currentDescription = new ArrayList<>();
      } else if (currentName != null) {
        currentDescription.add(line);
      } else if (!trimmed.isEmpty()) {
        throw new InjectorException("Content before first constant header at data line " + (i + 1));
      }
    }

    if (currentName != null) {
      entries.add(new ConstantEntry(currentName, trimBlankLines(currentDescription)));
    }
    if (entries.isEmpty()) {
      throw new InjectorException("No constants found in data file");
    }
    return entries;
  }

  private static String stripBom(String line) {
    if (!line.isEmpty() && line.charAt(0) == '\uFEFF') {
      return line.substring(1);
    }
    return line;
  }

  private static boolean isValidIdentifier(String value) {
    if (value.isEmpty()) {
      return false;
    }
    if (!isAsciiIdentifierStart(value.charAt(0))) {
      return false;
    }
    boolean hasNameCharacter = value.charAt(0) != '_';
    for (int i = 1; i < value.length(); i++) {
      char ch = value.charAt(i);
      if (!isAsciiIdentifierPart(ch)) {
        return false;
      }
      hasNameCharacter = hasNameCharacter || ch != '_';
    }
    return hasNameCharacter;
  }

  private static boolean isAsciiIdentifierStart(char ch) {
    return ch == '_' || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
  }

  private static boolean isAsciiIdentifierPart(char ch) {
    return isAsciiIdentifierStart(ch) || (ch >= '0' && ch <= '9');
  }

  private static List<String> trimBlankLines(List<String> lines) {
    int start = 0;
    int end = lines.size();
    while (start < end && lines.get(start).trim().isEmpty()) {
      start++;
    }
    while (end > start && lines.get(end - 1).trim().isEmpty()) {
      end--;
    }
    return new ArrayList<>(lines.subList(start, end));
  }

  private static MarkerRegion findMarkerRegion(String source, String startMarker, String endMarker) {
    int startMarkerIndex = source.indexOf(startMarker);
    if (startMarkerIndex < 0) {
      throw new InjectorException("Start marker not found: " + startMarker);
    }

    int endMarkerIndex = source.indexOf(endMarker, startMarkerIndex + startMarker.length());
    if (endMarkerIndex < 0) {
      throw new InjectorException("End marker not found after start marker: " + endMarker);
    }

    int startLineStart = lineStart(source, startMarkerIndex);
    int startLineEnd = lineEnd(source, startMarkerIndex);
    int endLineStart = lineStart(source, endMarkerIndex);
    if (endLineStart <= startLineEnd) {
      throw new InjectorException("End marker must be on a line after the start marker");
    }

    String indent = leadingSpaces(source, startLineStart, startLineEnd);

    return new MarkerRegion(startLineEnd, endLineStart, indent);
  }

  private static int lineStart(String text, int index) {
    int previousNewline = text.lastIndexOf('\n', index);
    return previousNewline < 0 ? 0 : previousNewline + 1;
  }

  private static int lineEnd(String text, int index) {
    int newlineIndex = text.indexOf('\n', index);
    if (newlineIndex < 0) {
      return text.length();
    }
    return newlineIndex + 1;
  }

  private static String leadingSpaces(String text, int lineStart, int lineEnd) {
    int index = lineStart;
    while (index < lineEnd && text.charAt(index) == ' ') {
      index++;
    }
    return text.substring(lineStart, index);
  }

  private static String generate(
      SourceType sourceType, List<ConstantEntry> entries, String indent, String newline) {
    StringBuilder output = new StringBuilder();
    output.append(indent)
        .append(sourceType.lineCommentPrefix)
        .append(' ')
        .append(GENERATED_WARNING)
        .append(newline);

    for (ConstantEntry entry : entries) {
      output.append(indent).append(newline);
      if (sourceType == SourceType.JAVA) {
        appendJavaConstant(output, entry, indent, newline);
      } else if (sourceType == SourceType.PYTHON) {
        appendPythonConstant(output, entry, indent, newline);
      } else {
        appendPhpConstant(output, entry, indent, newline);
      }
    }

    return output.toString();
  }

  private static void appendJavaConstant(
      StringBuilder output, ConstantEntry entry, String indent, String newline) {
    for (String descriptionLine : entry.descriptionLines) {
      output.append(indent).append("///");
      if (!descriptionLine.isEmpty()) {
        output.append(' ').append(descriptionLine);
      }
      output.append(newline);
    }
    output.append(indent)
        .append("public static final String ")
        .append(entry.name)
        .append(" = \"")
        .append(escapeJavaString(toCamelCase(entry.name)))
        .append("\";")
        .append(newline);
  }

  private static void appendPhpConstant(
      StringBuilder output, ConstantEntry entry, String indent, String newline) {
    if (!entry.descriptionLines.isEmpty()) {
      output.append(indent).append("/**").append(newline);
      for (String descriptionLine : entry.descriptionLines) {
        output.append(indent).append(" *");
        if (!descriptionLine.isEmpty()) {
          output.append(' ').append(descriptionLine.replace("*/", "* /"));
        }
        output.append(newline);
      }
      output.append(indent).append(" */").append(newline);
    }
    output.append(indent).append("static function ").append(entry.name).append("()").append(newline);
    output.append(indent).append("{").append(newline);
    output.append(indent)
        .append("    return \"")
        .append(escapePhpString(toCamelCase(entry.name)))
        .append("\";")
        .append(newline);
    output.append(indent).append("}").append(newline);
  }

  private static void appendPythonConstant(
      StringBuilder output, ConstantEntry entry, String indent, String newline) {
    output.append(indent)
        .append(entry.name)
        .append(" = \"")
        .append(escapePythonString(toCamelCase(entry.name)))
        .append("\"")
        .append(newline);
    output.append(indent).append("\"\"\"").append(newline);
    for (String descriptionLine : MarkdownToRstConverter.convert(entry.descriptionLines)) {
      output.append(indent).append(descriptionLine.replace("\"\"\"", "\"\"\\\"")).append(newline);
    }
    output.append(indent).append("\"\"\"").append(newline);
  }

  private static String toCamelCase(String constantName) {
    StringBuilder output = new StringBuilder();
    String[] parts = constantName.split("_+");
    for (String part : parts) {
      if (part.isEmpty()) {
        continue;
      }
      String lowerPart = part.toLowerCase(Locale.ROOT);
      if (output.length() == 0) {
        output.append(lowerPart);
      } else {
        output.append(Character.toUpperCase(lowerPart.charAt(0)));
        output.append(lowerPart.substring(1));
      }
    }
    return output.toString();
  }

  private static String escapeJavaString(String value) {
    return value.replace("\\", "\\\\").replace("\"", "\\\"");
  }

  private static String escapePhpString(String value) {
    return value.replace("\\", "\\\\").replace("\"", "\\\"");
  }

  private static String escapePythonString(String value) {
    return value.replace("\\", "\\\\").replace("\"", "\\\"");
  }

  private static String detectNewline(String source) {
    int newlineIndex = source.indexOf('\n');
    if (newlineIndex > 0 && source.charAt(newlineIndex - 1) == '\r') {
      return "\r\n";
    }
    return "\n";
  }

  private static final class MarkdownToRstConverter {
    private static final Pattern MARKDOWN_LINK =
        Pattern.compile("\\[([^\\]]+)]\\(([^)]+)\\)");
    private static final Pattern MARKDOWN_CODE =
        Pattern.compile("`([^`]+)`");
    private static final Pattern MARKDOWN_UNDERSCORE_STRONG =
        Pattern.compile("__(.+?)__");
    private static final Pattern HTML_BREAK =
        Pattern.compile("(?i)<br\\s*/?>");

    private MarkdownToRstConverter() {
    }

    private static List<String> convert(List<String> markdownLines) {
      List<String> rstLines = new ArrayList<>();
      for (int i = 0; i < markdownLines.size(); i++) {
        if (isMarkdownTableStart(markdownLines, i)) {
          i = appendRstListTable(rstLines, markdownLines, i);
        } else {
          rstLines.add(markdownInlineToRst(markdownLines.get(i)));
        }
      }
      return rstLines;
    }

    private static boolean isMarkdownTableStart(List<String> lines, int index) {
      return index + 1 < lines.size()
          && isMarkdownTableRow(lines.get(index))
          && isMarkdownTableSeparator(lines.get(index + 1));
    }

    private static boolean isMarkdownTableRow(String line) {
      String trimmed = line.trim();
      return trimmed.startsWith("|") && trimmed.endsWith("|") && trimmed.length() > 1;
    }

    private static boolean isMarkdownTableSeparator(String line) {
      if (!isMarkdownTableRow(line)) {
        return false;
      }
      List<String> cells = splitMarkdownTableRow(line);
      if (cells.isEmpty()) {
        return false;
      }
      for (String cell : cells) {
        String trimmed = cell.trim();
        if (!trimmed.contains("-")) {
          return false;
        }
        for (int i = 0; i < trimmed.length(); i++) {
          char ch = trimmed.charAt(i);
          if (ch != '-' && ch != ':' && ch != ' ') {
            return false;
          }
        }
      }
      return true;
    }

    private static int appendRstListTable(
        List<String> output, List<String> markdownLines, int tableStart) {
      output.add(".. list-table::");
      output.add("   :header-rows: 1");
      output.add("");
      appendRstListTableRow(output, splitMarkdownTableRow(markdownLines.get(tableStart)));

      int index = tableStart + 2;
      while (index < markdownLines.size() && isMarkdownTableRow(markdownLines.get(index))) {
        appendRstListTableRow(output, splitMarkdownTableRow(markdownLines.get(index)));
        index++;
      }
      return index - 1;
    }

    private static void appendRstListTableRow(List<String> output, List<String> cells) {
      for (int i = 0; i < cells.size(); i++) {
        String prefix = i == 0 ? "   * - " : "     - ";
        output.add(prefix + markdownInlineToRst(normalizeTableCell(cells.get(i))));
      }
    }

    private static List<String> splitMarkdownTableRow(String line) {
      String trimmed = line.trim();
      if (trimmed.startsWith("|")) {
        trimmed = trimmed.substring(1);
      }
      if (trimmed.endsWith("|")) {
        trimmed = trimmed.substring(0, trimmed.length() - 1);
      }

      String[] cells = trimmed.split("\\|", -1);
      List<String> result = new ArrayList<>();
      for (String cell : cells) {
        result.add(cell.trim());
      }
      return result;
    }

    private static String normalizeTableCell(String value) {
      return HTML_BREAK.matcher(value).replaceAll(" ").replaceAll("\\s+", " ").trim();
    }

    private static String markdownInlineToRst(String line) {
      String output = HTML_BREAK.matcher(line).replaceAll(" ");
      output = replaceMarkdownCode(output);
      output = MARKDOWN_UNDERSCORE_STRONG.matcher(output).replaceAll("**$1**");
      output = replaceMarkdownLinks(output);
      return output;
    }

    private static String replaceMarkdownLinks(String line) {
      Matcher matcher = MARKDOWN_LINK.matcher(line);
      StringBuilder output = new StringBuilder();
      while (matcher.find()) {
        matcher.appendReplacement(
            output,
            Matcher.quoteReplacement("`" + matcher.group(1) + " <" + matcher.group(2) + ">`_"));
      }
      matcher.appendTail(output);
      return output.toString();
    }

    private static String replaceMarkdownCode(String line) {
      Matcher matcher = MARKDOWN_CODE.matcher(line);
      StringBuilder output = new StringBuilder();
      while (matcher.find()) {
        matcher.appendReplacement(output, Matcher.quoteReplacement("``" + matcher.group(1) + "``"));
      }
      matcher.appendTail(output);
      return output.toString();
    }
  }

  private enum SourceType {
    JAVA("//"),
    PHP("//"),
    PYTHON("#");

    private final String lineCommentPrefix;

    SourceType(String lineCommentPrefix) {
      this.lineCommentPrefix = lineCommentPrefix;
    }

    private static SourceType fromPath(Path path) {
      String fileName = path.getFileName().toString().toLowerCase(Locale.ROOT);
      if (fileName.endsWith(".java")) {
        return JAVA;
      }
      if (fileName.endsWith(".php")) {
        return PHP;
      }
      if (fileName.endsWith(".py")) {
        return PYTHON;
      }
      throw new InjectorException("Unsupported input source file extension: " + path);
    }
  }

  private static final class ConstantEntry {
    private final String name;
    private final List<String> descriptionLines;

    private ConstantEntry(String name, List<String> descriptionLines) {
      this.name = name;
      this.descriptionLines = descriptionLines;
    }
  }

  private static final class MarkerRegion {
    private final int replacementStart;
    private final int replacementEnd;
    private final String indent;

    private MarkerRegion(int replacementStart, int replacementEnd, String indent) {
      this.replacementStart = replacementStart;
      this.replacementEnd = replacementEnd;
      this.indent = indent;
    }
  }

  private static final class InjectorException extends RuntimeException {
    private InjectorException(String message) {
      super(message);
    }
  }
}
