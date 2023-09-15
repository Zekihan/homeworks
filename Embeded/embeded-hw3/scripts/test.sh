#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd $DIR

gcc ../source/pp.c -o ../out/pp

for i in {1..100}
do
    echo "------------------------------------"
    echo run $i
    ../out/pp
done