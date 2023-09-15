#include<stdio.h>
#include<string.h>

int main(int argc, char **argv){


    /* get first arg */
    char *product = argv[1];

    char* market_dir = "./market.txt";

    int count = 0;

    FILE *input_file;
    int buff_size = 255;
    char buff[buff_size];

    /* open the file for reading */
    input_file = fopen(market_dir, "r");

    /* counts the specific item in market.txt */
    /* same parsing method as main.c */
    char *ptr;
    while( fgets ( buff, buff_size, input_file ) != NULL )
    {
        char *day = strtok(buff, ",");
        char *customer = strtok(NULL, ",");
        ptr = strtok(NULL, ",");
        do{
            if(ptr[0] == ' '){
                ptr = ptr+1;
            }
            if(ptr[strlen(ptr)-1] == '\n'){
                ptr[strlen(ptr)-1] = '\0';
            }
            if(strcmp(ptr, product) == 0){
                count++;
            }
            ptr = strtok(NULL, ",");

        }while (ptr != NULL);

    }
    
    printf("There is %d \"%s\".\n", count,product);

    fclose(input_file);

    return 0;
}