# Plan

I need a GitHub action added. 

For each commit to a branch in this repo (excluding main) it will:

1. Do the following checks:
    1. Check the branch now has any changes compared to the base in the exact list of files (request.md, response.md, reason_codes.md).
    2. Check if the PR exists for this branch. 
2. If all the checks above pass, proceed with the following steps, otherwise stop here
3. Check out another (target) repository X (will be specified), the branch with the same name
4. If such a branch does not exist, create it
5. Run a script (will be specified) from the current repo
6. The script will change some files in X
7. Add/commit all changed files to that branch in X. Use the commit message from the current commit
8. Push the branch to the remote repository X
9. Create a PR in X with the same name as the name of this PR, but only if it doesn't exist.
10. Add a new comment to the current PR with a link to the PR in X
                                    
## Requirements

1. Implement all the logic for checks (items 1, 2 above) in a single bash script `gh_check.sh`.
2. Implement all the main logic (items 3+) in a single bash script `gh_process.sh`. It will take a target repository as a parameter.
   - the idea here is that in future we will process more than one target repository
3. The actual github action will delegate to the scripts above
4. In the result scripts add a brief description at the top of each function. 
5. A single GitHub token (fine-grained personal access token) will be configured. A secret named `SDK_AUTOMATION_TOKEN` will be used to store it.
