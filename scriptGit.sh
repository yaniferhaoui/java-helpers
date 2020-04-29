git config credential.helper store
git fetch
git merge
git add .
if [ -n "$1" ]; then
    git commit -m "$1"
    git push
fi
git pull
