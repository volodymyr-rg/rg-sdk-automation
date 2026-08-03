`Injector.java` is a runnable class with a main method. Its purpose is to generate a consistent list of constants across multiple SDKs based on a data file.

For the implementation we only use Java SDK, no external dependencies.

The program will accept the next CLI params:
- start marker. Example: `/* --- start response constants --- */`
- end marker. Example: `/* --- end response constants --- */`
- data.md file path. Example: `src/main/java/rg_sdks_updater/response.md`
- input source file path. Example: `src/main/java/rg_sdks_updater/GatewayResponse.java`

What the program will do:
- read the data.md file
- parse the constant values from it like so:
- any header (starts with `#`) represents a constant name: `# CONSTANT_NAME` -> `CONSTANT_NAME`
- any content after the header (but before the next header) represents a constant description (will go to javadoc/phpdoc/etc.)
- find the start and end markers in the input source file
- replace the content between the markers with a list of generated constants based on the data from the data.md file
- after the start marker, the very first line must be a comment with a warning that the content is generated and should not be edited manually
- markers must be preserved
- for the constant value use a camel-cased variant of the constant name. Example: `PARTIAL_AUTH` -> `partialAuth`
- write the result to the standard output
- in case of any errors (example: an input file not found, start or end marker not found in a file, etc.), print the error message to the standard error output and exit with a non-zero exit code
       
Imagine the data.md file has a section

```
# CONSTANT_NAME

Constant *description* __example__
```

Based on the file extension of the input source file generate the constants in the appropriate syntax. More specifically:

For php use this declaration form:
```
/**
 * Constant *description* __example__
 */
static function CONSTANT_NAME()
{
   return "constantName";
}
```

For the phpdocs use standard `/** */` syntax, as it supports markdown by default.


For java:
```
/// Constant *description* __example__
public static final String CONSTANT_NAME = "constantName";
```

For the javadocs use the markdown-flavored syntax (block of text, each line starts `///`).

## Addendum 1

Add support for Python. Use this declaration form:
```
CONSTANT_NAME = "constantName"
"""
Constant *description* __example__
"""
```

(we are using python docstrings)

## Addendum 2

Since Python's docstrings only support reStructuredText, the descriptions must be converted from markdown to reStructuredText. Let's have a simple conversion function. Let's not make it too generic, need to cover very basic cases, just enough for us. But tables, lists and links should be covered.

## Addendum 3

- Put all the logic, including constants, related to markdown -> reStructuredText conversion into a separate inner class.

## Addendum 4

Fix indentation. Right now the indentation is not properly created during code generation. For example, we generate:

```java
  /* --- start response constants --- */
// WARNING: This content is generated. Do not edit manually.

/// Six digit code returned by the banking institution in response to successful auth-only, ticket, and purchase transactions.
public static final String AUTH_NO = "authNo";
```

Must be:

```java
  /* --- start response constants --- */
  // WARNING: This content is generated. Do not edit manually.
  
  /// Six digit code returned by the banking institution in response to successful auth-only, ticket, and purchase transactions.
  public static final String AUTH_NO = "authNo";
```

I.e. indentation should be equal to the number of spaces that precede the first non-space character of a string with a start marker. 