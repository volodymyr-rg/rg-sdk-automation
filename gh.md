# Plan

I need a GitHub action added. 

For each PR created or pushed to, it will:

1. Check the PR has any changes in the exact list of files (request.md, response.md, reason_codes.md).
2. If positive, proceed with the following steps, otherwise stop here
3. Check out another (target) repository X (will be specified), the branch with the same name
4. If such a branch does not exist, create it
5. Run a specific script ("injector") from the current repo
6. The script will change some files in X
7. Add/commit all changed files to that branch in X. Use the commit message from the current commit
8. Push the branch to the remote repository X
9. Create a PR in X with the same name as the name of this PR, but only if it doesn't exist.
10. Add a new comment to the current PR with a link to the PR in X
                                    
## Requirements

1. Implement all the logic for checks (items 1 above) in a single bash script `gh_check.sh`.
2. Implement all the main logic (items 3+) in a single bash script `gh_process.sh`. It will take a target repository as a parameter.
   - the idea here is that in future we will process more than one target repository
3. The actual GitHub action will delegate to the scripts above
4. In the result scripts add a brief description at the top of each function. 
5. The concrete command for "injector" will be specified later by human, for now add a placeholder function `inject` to `gh_process.sh` and use it.
6. A single GitHub token (fine-grained personal access token) with sufficient permissions will be configured. A secret named `GH_TOKEN` will be used to store it.
