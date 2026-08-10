#!/usr/bin/env bash

set -euo pipefail

SOURCE_REPOSITORY_ROOT=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)
readonly SOURCE_REPOSITORY_ROOT
WORK_DIRECTORY=

# Print an error message and terminate the script.
die() {
  printf 'gh_process.sh: %s\n' "$*" >&2
  exit 1
}

# Verify that required commands and workflow context are available.
validate_environment() {
  local target_repository=$1
  local command

  [[ -n $target_repository ]] || die 'target repository is required (for example, owner/repository)'
  [[ -n ${GH_TOKEN:-} ]] || die 'GH_TOKEN is required'
  [[ -n ${GITHUB_REPOSITORY:-} ]] || die 'GITHUB_REPOSITORY is required'
  [[ -n ${PR_NUMBER:-} ]] || die 'PR_NUMBER is required'
  [[ -n ${PR_TITLE:-} ]] || die 'PR_TITLE is required'
  [[ -n ${PR_BRANCH:-} ]] || die 'PR_BRANCH is required'

  for command in gh git mktemp; do
    command -v "$command" >/dev/null 2>&1 || die "required command not found: ${command}"
  done

  git check-ref-format --branch "$PR_BRANCH" >/dev/null 2>&1 \
    || die "invalid pull request branch name: ${PR_BRANCH}"
}

# Configure GitHub CLI as Git's credential helper for authenticated pushes.
configure_git_authentication() {
  gh auth setup-git
}

# Remove the temporary target checkout created during processing.
cleanup() {
  if [[ -n ${WORK_DIRECTORY:-} ]]; then
    rm -rf -- "$WORK_DIRECTORY"
  fi
}

# Clone the target repository and check out the matching branch, creating it if absent.
checkout_target_repository() {
  local target_repository=$1
  local target_directory=$2
  local branch=$3

  gh repo clone "$target_repository" "$target_directory" -- --quiet
  git -C "$target_directory" fetch --quiet origin

  if git -C "$target_directory" ls-remote --exit-code --heads origin "refs/heads/${branch}" \
      >/dev/null 2>&1; then
    git -C "$target_directory" checkout --quiet -B "$branch" "origin/$branch"
  else
    git -C "$target_directory" checkout --quiet -b "$branch"
  fi
}

# Run the SDK injector against the checked-out target repository.
inject() {
  local target_directory=$1

  JAVABIN='${JAVA_HOME_21_X64}/bin'
  JAVA="$JAVABIN/java"
  JAVAC="$JAVABIN/javac"
  TARGET="$WORK_DIRECTORY/target"
  INJECTOR_DIR="$SOURCE_REPOSITORY_ROOT/rg_sdk_updater/"

  compiled () {
    if [[ "$INJECTOR_DIR/Injector.class" -nt "$INJECTOR_DIR/Injector.java" ]]
    then
      echo "Already compiled..."
    fi
    cd "$INJECTOR_DIR"
    "$JAVAC" Injector.java
  }

#  @goal cleaned
#    cd "$CODE"
#    rm *.class

#  @goal run @params SRC
#  @depends_on compiled
  run() {
    local file="$1"
    "$JAVA" -cp "$INJECTOR_DIR" rg_sdks_updater.Injector \
      '--- start response constants ---' \
      '--- end response constants ---' \
      "$SRC/response.md" \
      "$TARGET/$file" > /tmp/delme
      mv /tmp/delme "$TARGET/$file"
  }

  compiled

  # TODO call based on repo name
  run 'GatewayResponse.php'

#  @goal runall
#    @depends_on run @args 'GatewayResponse.php'
#    @depends_on run @args 'GatewayResponse.java'
#    @depends_on run @args 'RocketGate.py'
}

# Commit and push all staged injector changes.
commit_and_push_changes() {
  local target_directory=$1
  local branch=$2
  local commit_message_file=$3

  git -C "$target_directory" \
    -c "user.name=${GIT_AUTHOR_NAME:-github-actions[bot]}" \
    -c "user.email=${GIT_AUTHOR_EMAIL:-41898282+github-actions[bot]@users.noreply.github.com}" \
    commit --quiet --file "$commit_message_file"
  git -C "$target_directory" push --set-upstream origin "HEAD:refs/heads/${branch}"
}

# Find the open target pull request or create one with the source pull request title.
ensure_target_pull_request() {
  local target_repository=$1
  local branch=$2
  local default_branch=$3
  local pull_request_url

  pull_request_url=$(gh pr list \
    --repo "$target_repository" \
    --head "$branch" \
    --state open \
    --json url \
    --jq '.[0].url // empty')

  if [[ -z $pull_request_url ]]; then
    pull_request_url=$(gh pr create \
      --repo "$target_repository" \
      --base "$default_branch" \
      --head "$branch" \
      --title "$PR_TITLE" \
      --body "Automated SDK update from ${GITHUB_REPOSITORY}#${PR_NUMBER}.")
  fi

  printf '%s\n' "$pull_request_url"
}

# Add the target pull request link to the source pull request.
comment_on_source_pull_request() {
  local target_repository=$1
  local target_pull_request_url=$2

  gh pr comment "$PR_NUMBER" \
    --repo "$GITHUB_REPOSITORY" \
    --body "SDK update for \`${target_repository}\`: ${target_pull_request_url}"
}

# Coordinate target checkout, injection, publishing, and pull request linking.
process_repository() {
  local target_repository=$1
  local target_directory
  local commit_message_file
  local default_branch
  local target_pull_request_url

  WORK_DIRECTORY=$(mktemp -d)
  trap cleanup EXIT
  target_directory="$WORK_DIRECTORY/target"
  commit_message_file="$WORK_DIRECTORY/commit-message"
  git -C "$SOURCE_REPOSITORY_ROOT" log -1 --format=%B > "$commit_message_file"

  default_branch=$(gh repo view "$target_repository" --json defaultBranchRef \
    --jq '.defaultBranchRef.name')
  [[ -n $default_branch ]] || die "could not determine default branch for ${target_repository}"

  checkout_target_repository "$target_repository" "$target_directory" "$PR_BRANCH"
  inject "$target_directory"

  git -C "$target_directory" add --all
  if git -C "$target_directory" diff --cached --quiet; then
    if [[ $(git -C "$target_directory" rev-list --count \
        "origin/${default_branch}..HEAD") -eq 0 ]]; then
      printf 'Injector produced no changes; nothing to publish.\n'
      return 0
    fi
    printf 'Injector produced no new changes; using the existing target branch.\n'
  else
    commit_and_push_changes "$target_directory" "$PR_BRANCH" "$commit_message_file"
  fi

  target_pull_request_url=$(ensure_target_pull_request \
    "$target_repository" "$PR_BRANCH" "$default_branch")
  comment_on_source_pull_request "$target_repository" "$target_pull_request_url"
}

# Validate input and process the requested target repository.
main() {
  [[ $# -eq 1 ]] || die 'usage: gh_process.sh <target-repository>'
  validate_environment "$1"
  configure_git_authentication
  process_repository "$1"
}

main "$@"
