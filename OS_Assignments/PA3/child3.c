#include<stdio.h>
#include<stdlib.h>
#include<string.h>

int get_money(char *product,char *price_dir);

int main(int argc, char **argv){

    /* get first arg */
    char *arg = argv[1];
    
    char* market_dir = "./market.txt";
    char* price_dir = "./price.txt";

    /* initialise the total spend with 0 */
    int spend = 0;

    FILE *input_file;
    int buff_size = 255;
    char buff[buff_size];
    
    /* open the file for reading */
    input_file = fopen(market_dir, "r");

    /* parse market.txt and look for customer */
    /* for every transaction customer is in print items */
    /* and add their values into total spent */
    char *ptr;
    while( fgets ( buff, buff_size, input_file ) != NULL )
    {
        char *day = strtok(buff, ",");
        char *customer = strtok(NULL, ",");
        if(customer[strlen(customer)-1] == *arg){
            printf("%s : ",day);
            /* i didnt calculated every item directly because if i did that */
            /* strtok in here gets broken because of the getmoney method's strtok */
            /* so i waited until this token became useless */
            int count = 0;
            char *items[20];
            ptr = strtok(NULL, ",");
            do{
                if(ptr[0] == ' '){
                    ptr = ptr+1;
                }
                if(ptr[strlen(ptr)-1] == '\n'){
                    ptr[strlen(ptr)-1] = '\0';
                }
                printf("%s, ",ptr);
                items[count] = ptr;
                count++;
                ptr = strtok(NULL, ",");

            }while (ptr != NULL);
            for (int i = 0; i < count; ++i) {
                spend += get_money(items[i],price_dir);
            }
            printf("\n");
        }
    }
    
    printf("Total spend : %d\n",spend);
    
    fclose(input_file);

    return 0;
}

/* opens price.txt looks for item and return its price */
int get_money(char *product,char *price_dir){
    
    int output = -1;
    FILE *price_file;
    int buff_size = 255;
    char buff[buff_size];

    price_file = fopen(price_dir, "r");

    while( fgets ( buff, buff_size, price_file ) != NULL )
    {

        char *item = strtok(buff, ",");
        if(strcmp(item,product) == 0){
            char *price = strtok(NULL, ",");
            output = atoi(price) ;
            break;
        }

    }
    fclose(price_file);
    return output;
}