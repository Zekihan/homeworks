#!/bin/bash
echo "compiling the output."
gcc VigenereCipher.c VigenereCipherApp.c -o op.out
echo "running the output."
./op.out