#!/bin/bash
echo "compiling the output."
cd code
gcc main.c stack.c binary_search_tree.c -g -o ../op.out
echo "running the output."
../op.out