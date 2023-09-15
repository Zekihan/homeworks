#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include <time.h>
#include <unistd.h>
#include <wait.h>

#define BUFFER_SIZE 80
#define READ_END 0
#define WRITE_END 1
#define READ_END2 2
#define WRITE_END2 3

int fd[4];

int write_file(char* file_dir,char* data);

void parent(){

    /* generate random task */
    int taskID = rand() % 5 +1;

    char task[BUFFER_SIZE] = {'\000'};
    /* size is BUFFER_SIZE-1 because task is always 1 char long */
    char product[BUFFER_SIZE-1] = {'\000'};

    /* char '&' is used as markers again */
    sprintf(task, "&%d", taskID);

    /* if task is 2 get product name */
    if (taskID == 2){

        char temp[BUFFER_SIZE-1] = {'\000'};

        /* when waiting here other children can output */
        /* so this might not be seen but it still waits for user input */
        printf("Enter product name: ");
        scanf(" %s",temp);
        sprintf(product, "&%s&", temp);
        strcat(task,product);
    }

    /* if task is 3 get customer number */
    if (taskID == 3){

        char temp[BUFFER_SIZE-1] = {'\000'};

        /* when waiting here other children can output */
        /* so this might not be seen but it still waits for user input */
        printf("Enter customer number: ");
        scanf(" %s",temp);
        sprintf(product, "&%s&", temp);
        strcat(task,product);
    }
    close(fd[READ_END]);
    write(fd[WRITE_END], task, BUFFER_SIZE);
}

int child(){

    char task[BUFFER_SIZE] = {'\000'};

    /* read from pipe */
    read(fd[READ_END], task, BUFFER_SIZE);
    if(strlen(task) == 0){
        printf("Error in reading pipe\n");
        return -1;
    }

    char *taskID = strtok(task, "&");

    /* if task is 2 parse product name */
    if (strcmp(taskID,"2") != 0){

        /* format the args of exec */
        char child_file[BUFFER_SIZE] = {'\000'};
        sprintf(child_file, "child%s.o", taskID);
        printf("Start of task \"%s\".\n",child_file);
        char *product = strtok(NULL, "&");

        char* argv[3];
        argv[0] = child_file;
        argv[1] = product;
        argv[2] = NULL;

        /* get stdout of exec to pipe */
        dup2 (fd[WRITE_END2], STDOUT_FILENO);
        if(execv(argv[0], argv)==-1)
            printf("Error in calling exec \"%s\"!!\n",task);
        return -1;
    }
    /* if task is 3 parse customer number */
    else if (strcmp(taskID,"3") != 0){

        /* format the args of exec */
        char child_file[BUFFER_SIZE] = {'\000'};
        sprintf(child_file, "child%s.o", taskID);
        printf("Start of task \"%s\".\n",child_file);
        char *product = strtok(NULL, "&");

        char* argv[3];
        argv[0] = child_file;
        argv[1] = product;
        argv[2] = NULL;

        /* get stdout of exec to pipe */
        dup2 (fd[WRITE_END2], STDOUT_FILENO);
        if(execv(argv[0], argv)==-1)
            printf("Error in calling exec \"%s\"!!\n",task);
        return -1;
    } else{

        /* format the args of exec */
        char child_file[BUFFER_SIZE] = {'\000'};
        sprintf(child_file, "child%s.o", taskID);
        printf("Start of task \"%s\".\n",child_file);

        char* argv[2];
        argv[0] = child_file;
        argv[1] = NULL;

        /* get stdout of exec to pipe */
        dup2 (fd[WRITE_END2], STDOUT_FILENO);
        if(execv(argv[0], argv)==-1)
            printf("Error in calling exec \"%s\"!!\n",task);
        return -1;
    }
}

int main(){


    /* initialisation of rand */
    time_t t;
    srand((unsigned) time(&t));

    /* initialisation of pipe */
    if(pipe(fd)==-1) {
        printf("pipe cannot create.\n");
        return -1;
    }

    /* IN MARKET.TXT WHITE SPACES SENSITIVE */
    /* SO UNNECESSARY ' ' CHAR MAKES IT UNIQUE ITEM */
    char* market_dir = "./market.txt";
    char* price_dir = "./price.txt";


    /* open and clear contents of price.txt */
    FILE *tf = fopen (price_dir,"w");
    fprintf (tf, "%s", "");
    fclose (tf);

    /* string to contain unique items */
    char *items = malloc(sizeof(char)*1500);

    FILE *input_file;
    int buff_size = 255;
    char buff[buff_size];

    input_file = fopen(market_dir, "r");

    /* parse the market.txt */
    char *ptr;
    while( fgets ( buff, buff_size, input_file ) != NULL )
    {

        char *day = strtok(buff, ",");
        char *customer = strtok(NULL, ",");
        ptr = strtok(NULL, ",");
        do{
            /* first char is empty space skip it */
            if(ptr[0] == ' '){
                ptr = ptr+1;
            }
            /* last char is line break skip it */
            if(ptr[strlen(ptr)-1] == '\n'){
                ptr[strlen(ptr)-1] = '\0';
                if(ptr[strlen(ptr)-2] == '\r'){
                    ptr[strlen(ptr)-1] = '\0';
                }
            }

            /* used '&' as marker that separates items */
            /* looking if item inside of items already */
            /* if not add it with random price */
            /* and append to price.txt */
            char search[50];
            sprintf(search, "&%s&", ptr);
            if(strstr(items, search) == NULL) {
                strcat(items,"&");
                strcat(items,ptr);
                strcat(items,"&");
                int price = rand() % 100 +1;
                char temp[50];
                sprintf(temp, "%s,%d\n", ptr, price);
                write_file(price_dir, temp);
            }
            ptr = strtok(NULL, ",");

        }while (ptr != NULL);

    }
    fclose(input_file);

    /* create 7 child */
    int pid1,pid2,pid3,ppid;
    ppid = getpid();
    pid1 = fork();
    pid2 = fork();
    pid3 = fork();

    if(pid1 == -1 || pid2 == -1 || pid3 == -1){
        printf("error creating child.\n");
        return -1;
    }

    /* create 7 task */
    if(getpid()==ppid){
        parent();
        parent();
        parent();
        parent();
        parent();
        parent();
        parent();

    }else{
        child();
    }



    /* read and print second pipe */
    char buffer[4096];
    while (1) {
        int count = read(fd[READ_END2], buffer, sizeof(buffer));
        if (count == 0) {
            break;
        } else {
            printf("%.*s", count, buffer);
        }
    }
    close(fd[READ_END2]);

    wait(NULL);
    printf("\nEnd of the parent process.\n");
    return 0;
}

int write_file(char* file_dir,char* data){
    FILE * fp;

    /* open the file for writing*/
    fp = fopen (file_dir,"a");

    /* write data the file stream*/
    fprintf (fp, "%s", data);

    /* close the file*/
    fclose (fp);

    return 0;
}


