#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"VigenereCipher.h"

void encrypt_main();
void decrypt_main();

#define STRINGSIZE 200  

int main(){

    int query = 0;
    
    //user interface

    printf("\rWELCOME TO THE VIGENERE CIPHER\n");
    do{
        printf("If you want to encrypt press '1'.\n");
        printf("If you want to decrypt press '2'.\n");
        printf("If you want to quit press any other key.\n");
        printf("Waiting for input: ");

        //checking if input is a integer
        if (scanf(" %d",&query) != 0) {
            if (query == 1)
            {
                printf("Please use only ascii letters, no whitespaces.\n");
                printf("Charachter limit is 200 for both key and data.\n");
                encrypt_main();
            }
            else if (query == 2)
            {
                printf("Please use only ascii letters, no whitespaces.\n");
                printf("Charachter limit is 200 for both key and data.\n");
                decrypt_main();
            }
            else
            {
                break;
            }
        }
        else
        {
            break;
        }
    }while(query != 0);

    printf("\tGOODBYE.\nPLEASE COME BACK AGAIN!\n");

    return 0;
}

// to make more readable divided to two methods

void encrypt_main(){


    char base_key[STRINGSIZE];
    printf("Enter string encryption key: ");
    scanf(" %s",base_key);

    char plain_string[STRINGSIZE];
    printf("Enter string to be encrypted: ");
    scanf(" %s",plain_string);
    
    char encrypted_string[STRINGSIZE];
    strcpy(encrypted_string,encrypt(plain_string,base_key));
    printf("This is the encrypted version.\n\t%s\n------------------------------\n",encrypted_string);
}

void decrypt_main(){

    char base_key[STRINGSIZE];
    printf("Enter string decryption key: ");
    scanf(" %s",base_key);

    char raw_string[STRINGSIZE];
    printf("Enter string to be decrypted: ");
    scanf(" %s",raw_string);
    
    char dec[STRINGSIZE];
    strcpy(dec,decrypt(raw_string,base_key));
    printf("This is the decrypted version.\n\t%s\n------------------------------\n",dec);
}

