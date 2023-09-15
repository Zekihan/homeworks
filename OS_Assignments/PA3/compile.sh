#!/bin/bash
clear
clear
clear
echo "compiling the output."
cd code
gcc child1.c -o ./child1.o
gcc child2.c -o ./child2.o
gcc child3.c -o ./child3.o
gcc child4.c -o ./child4.o
gcc child5.c -o ./child5.o
gcc main.c -g -o ../op.out
echo "running the output."
../op.out
