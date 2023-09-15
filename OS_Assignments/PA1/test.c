#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"VigenereCipher.h"

int main(){

	int base_key_lenght = 0;
    int data_lenght = 0;


    printf("Enter string encryption key lenght: ");
    scanf(" %d",&base_key_lenght);
    printf("%d\n",base_key_lenght);

    char base_key[base_key_lenght+1];
    printf("Enter string encryption key: ");
    scanf(" %s",base_key);
    strupr(base_key);
    printf("%s\n",base_key);

}