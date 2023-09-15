#include<stdio.h>
#include<stdlib.h>
#include<string.h>

int main(){

    char* market_dir = "./market.txt";

    /* restricted with 19 customer max */
    /* because in market.txt customer id's start with 1 */
    int item_count[20] = {0};

    FILE *input_file;
    int buff_size = 255;
    char buff[buff_size];

    /* open market.txt */
    input_file = fopen(market_dir, "r");

    /* parse through market.txt and sum customers total item count. */
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
            int customer_id = customer[strlen(customer) - 1]-48;
            item_count[customer_id]++;

            ptr = strtok(NULL, ",");

        }while (ptr != NULL);

    }
    
    /* get the maximum of item_count */
    int max = 0;
    for (int i = 1; i < 20; ++i) {
        if(item_count[i] != 0){
            if (max < item_count[i]){
                max = item_count[i];
            }
        }
    }

    /* print who purchased most */
    printf("Customer(s) purchased most is(are):\n");
    for (int i = 1; i < 20; ++i) {
        if(item_count[i] == max){
            printf("\tCustomer %d\n",i);
        }
    }
    
    fclose(input_file);

    return 0;
}