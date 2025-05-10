# GIT

## **Main instructions used in andromeda project**

### Publishing a Clean `master` Branch from a Private Repository to a Public One

This guide explains how to publish only the **latest state** of your project to a public repository, **without including any commit history**. This is useful when sharing a clean version of a project while keeping the original history private.

---

## Steps

### 0. Requisite

The first time needs to add a public repository, example adding for Nebula Front App

```bash
git remote add public https://github.com/szymonderleta/nebula-front-app-public.git
```

then running command

```Bash
git remote -v
```

should return the similar output:

```Bash
origin  https://github.com/szymonderleta/nebula-front-app (fetch)
origin  https://github.com/szymonderleta/nebula-front-app (push)
public  https://github.com/szymonderleta/nebula-front-app-public.git (fetch)
public  https://github.com/szymonderleta/nebula-front-app-public.git (push)
```

### 1. Switch to the Source Branch
Switch to the branch that contains the latest code you want to publish:

```git
git checkout master_to_public
```

### 2. Create a New Branch Without History

Create a new branch with no commit history (an "orphan" branch), which retains your current files:
```git
git checkout --orphan new_master
```
### 3. Stage All Files

Since --orphan detaches the files from Git history, all files are now untracked. You need to add them manually:
```git
git add .
```
### 4. Commit the Files

Make the first commit on this new, clean history:
```git
git commit -m "Version 3.0.0 public release"
```
### 5. Push to the Public Repository

Force-push this new branch to the public repository's master branch:
```git
git push public new_master:master --force
```

What This Does

    git checkout --orphan creates a branch with no prior commits.

    git add . stages all existing files.

    git commit creates a fresh commit — the first in this branch's history.

    git push --force overwrites the remote master branch with your new commit, removing all previous history.

⚠️ Important Notes

    This process does not delete any files, only the commit history.

    Using --force will permanently replace the history on the remote master branch. Ensure no one depends on the old commit history before proceeding.

