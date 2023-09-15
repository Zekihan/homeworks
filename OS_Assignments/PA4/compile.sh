#!/bin/bash
clear
clear
clear
echo "compiling the output."
cd code
gcc main.c linkedList.c song.c -g -o ../op.out -pthread
echo "running the output."
../op.out
