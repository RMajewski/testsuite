#!/bin/bash

read=$(cat src/resources/version)
version=($read)

echo ${version[0]} ${version[1]} ${version[2]} $((${version[3]}+1)) > src/resources/version

git add src/resources/version

exit 0
