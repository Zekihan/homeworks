#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd $DIR

gcc ../source/pp.c -o ../out/pp_o2 -O2

for i in {1..10000}
do
    echo "------------------------------------"
    echo run $i
    ../out/pp_o2
done