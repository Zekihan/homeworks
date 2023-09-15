#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"VigenereCipher.h"

char* gen_key(char * base_key,int lenght);

char* encrypt(char * raw_str,char * raw_key){

	int lenght = strlen(raw_str);

	char key[lenght];
	strcpy(key,"");
    strcpy(key,gen_key(raw_key,lenght));

    
    char enc_str[lenght];
    strcpy(enc_str,"");
    char *return_str = enc_str;

    //encrypt char by char
    for (int i = 0; i < lenght; ++i)
    {
        char key_c = key[i];
        char raw_c = raw_str[i];
        //if it is lower case make capital and encrypt
        //if capital leave as it is and encrypt
        //else dont encrypt
        if (64<raw_c && raw_c<91)
        {
            raw_c = raw_c;
            //delete the off set of ascii
            //modulo make it return at the first char
            //add the offset
            char tmp[2] = {((raw_c-65)+(key_c-65))%26+65};
        }
        else if (96<raw_c && raw_c<123)
        {
            raw_c = raw_c - 32;
            char tmp[2] = {((raw_c-65)+(key_c-65))%26+65};
            strcat(enc_str,tmp);
        }
        else
        {
            char tmp[2] = {raw_c};
            strcat(enc_str,tmp);
        }
    
    }

    return return_str;
}

char* decrypt(char * raw_str,char * raw_key){
    
    int lenght = strlen(raw_str);
    
    char key[lenght];
	strcpy(key,"");
    strcpy(key,gen_key(raw_key,lenght));

    char dec_str[lenght];
    strcpy(dec_str,"");
    char *return_str = dec_str;

    //encrypt char by char
    for (int i = 0; i < lenght; ++i)
    {
        char key_c = key[i];
        char raw_c = raw_str[i];
        //if it is lower case make capital and decrypt
        //if capital leave as it is and decrypt
        //else dont decrypt since it is not encrypted
        if (64<raw_c && raw_c<91)
        {
            //substract the key for returning the normal value
            //offsets are canceled each other
            //adding 26 to make sure its positive value
            //modulo makes sure it wont exceed the alpabet limit
            //add the offset
            char tmp[2] = {((raw_c-key_c)+26)%26+65};
            strcat(dec_str,tmp);
        }
        else if (96<raw_c && raw_c<123)
        {
            raw_c = raw_c - 32;
            char tmp[2] = {((raw_c-key_c)+26)%26+65};
            strcat(dec_str,tmp);
        }
        else
        {
            char tmp[2] = {raw_c};
            strcat(dec_str,tmp);
        }
    }

    return return_str;
}

char* gen_key(char * base_key,int lenght){
    char temp_key[lenght];
    char *key = temp_key;
    strcpy(temp_key,"");
    int base_lenght = strlen(base_key);
    //growing string to lenght of plain text
    for (int i = 0; i < lenght; ++i)
    {
        char c = base_key[i%base_lenght];
        //ensures that all letter capital or not transfroms to capital 
        //if not a letter makes it 'A' so it basically dont encrpyt that one
        if (64<c && c<91)
        {
            c = c;
        }
        else if (96<c && c<123)
        {
            c = c - 32;
        }
        else
        {
            c = 'A';
        }
        char tmp[2] = {c};
        strcat(temp_key,tmp);
    }
    return key;
}