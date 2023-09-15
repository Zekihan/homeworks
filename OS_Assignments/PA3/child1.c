#include<stdio.h>
#include<string.h>

int main(){

    char* input_dir = "./price.txt";

    FILE *input_file;
    int buff_size = 255;
    char buff[buff_size];
    
    /* open the file for reading */
    input_file = fopen(input_dir, "r");

    /* parse and print the price.txt */
    while( fgets ( buff, buff_size, input_file ) != NULL )
    {
        char *item = strtok(buff, ",");
        char *price = strtok(NULL, ",");

        printf("%s  %s",item,price);

    }
    fclose(input_file);

    return 0;
}




