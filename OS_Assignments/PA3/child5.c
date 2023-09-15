#include<stdio.h>
#include<stdlib.h>
#include<string.h>

int get_money(char *product,char *price_dir);

int main(){

    char* market_dir = "./market.txt";
    char* price_dir = "./price.txt";

    /* restricted with 19 days max */
    /* because in market.txt day's start with 1 */
    int spend[20] = {0};

    FILE *input_file;
    int buff_size = 255;
    char buff[buff_size];

    /* open market.txt */
    input_file = fopen(market_dir, "r");

    /* parse market.txt and for every day add their total into spend */
    char *ptr;
    while( fgets ( buff, buff_size, input_file ) != NULL )
    {
        /* i didnt calculated every item directly because if i did that */
        /* strtok in here gets broken because of the getmoney method's strtok */
        /* so i waited until this token became useless */
        int count = 0;
        char *items[20] = {0};
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
            items[count] = ptr;
            count++;
            ptr = strtok(NULL, ",");

        }while (ptr != NULL);
        for (int i = 0; i < count; ++i) {
            int day_num = day[strlen(day) - 1]-48;
            spend[day_num] += get_money(items[i],price_dir);
        }

    }

    /* get the maximum of spend */
    int max = 0;
    for (int i = 1; i < 20; ++i) {
        if(spend[i] != 0){
            if (max < spend[i]){
                max = spend[i];
            }
        }
    }

    /* printMost profitable day */
    printf("Most profitable day(s) is(are):\n");
    for (int i = 1; i < 20; ++i) {
        if(spend[i] == max){
            printf("\tDay %d with %d\n",i,max);
        }
    }

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