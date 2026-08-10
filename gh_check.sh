#!/usr/bin/env bash

set -euo pipefail

readonly -a WATCHED_FILES=(
  request.md
  response.md
  reason_codes.md
)

# Print an error message and terminate the script.
die() {
  printf 'gh_check.sh: %s\n' "$*" >&2
  exit 2
}

# Report whether the pull request diff changes one of the watched files.
check_pr_changes() {
  local base_sha=$1
  local head_sha=$2
  local changed=false
  local -a pathspecs=()
  local watched_file

  git cat-file -e "${base_sha}^{commit}" 2>/dev/null \
    || die "base commit is not available: ${base_sha}"
  git cat-file -e "${head_sha}^{commit}" 2>/dev/null \
    || die "head commit is not available: ${head_sha}"

  for watched_file in "${WATCHED_FILES[@]}"; do
    pathspecs+=(":(top,literal)${watched_file}")
  done

  if [[ -n $(git diff --name-only "$base_sha...$head_sha" -- "${pathspecs[@]}") ]]; then
    changed=true
  fi

  printf 'changed=%s\n' "$changed"
  if [[ -n ${GITHUB_OUTPUT:-} ]]; then
    printf 'changed=%s\n' "$changed" >> "$GITHUB_OUTPUT"
  fi
}

# Validate command-line arguments and run the pull request check.
main() {
  [[ $# -eq 2 ]] || die 'usage: gh_check.sh <base-sha> <head-sha>'
  check_pr_changes "$1" "$2"
}

main "$@"
