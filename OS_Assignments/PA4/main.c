#include <string.h>
#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include "linkedList.h"

#define OK 0
#define INUSE 1

sem_t playlist1Lock;
sem_t playlist2Lock;
sem_t userplaylistLock;

int playlist1Status = OK;
int playlist2Status = OK;
int userplaylistStatus = OK;

Node *playlist1 = NULL;
Node *playlist2 = NULL;
Node *userplaylist = NULL;

int playlist1Count = 0;
int playlist2Count = 0;

char genre[15] = "";
int year = 0;

/* gets thread A 's lock and returns the playlist number */
int getTypeALock(){
    while(1){
        /* if both lists are empty return -1 */
        if(playlist1Count == 0 && playlist2Count == 0){
            return -1;
        }
        /* if playlist1 is not empty and playlist1 and userplaylist are not in use lock them */
        if(userplaylistStatus == OK && playlist1Status == OK && playlist1Count != 0){
            sem_wait(&playlist1Lock);
            sem_wait(&userplaylistLock);
            userplaylistStatus = INUSE;
            playlist1Status = INUSE;
            return 1;
        }
        /* if playlist2 is not empty and playlist2 and userplaylist are not in use lock them */
        if(userplaylistStatus == OK && playlist2Status == OK && playlist2Count != 0){
            sem_wait(&playlist2Lock);
            sem_wait(&userplaylistLock);
            userplaylistStatus = INUSE;
            playlist2Status = INUSE;
            return 2;
        }
    }
}
/* get playlist number and releases the playlist and userplaylist */
void releaseTypeALock(int listNum){
    if(listNum == 1){
        sem_post(&playlist1Lock);
        sem_post(&userplaylistLock);
        userplaylistStatus = OK;
        playlist1Status = OK;
    }
    if(listNum == 2){
        sem_post(&playlist2Lock);
        sem_post(&userplaylistLock);
        userplaylistStatus = OK;
        playlist2Status = OK;
    }
}
/* common thread method */
void * threads(void *id) {
    int threadID = *((int *)id);
    /* identify the thread and send them accordingly */

    /* THREAD B */
    if(threadID == 3){

        /* gets the lock deletes by genre and release the lock */
        /* always follows userplaylist, playlist1, playlist2 sequence */

        printf("thread B(%d) started\n",threadID);

        sem_wait(&userplaylistLock);
        userplaylistStatus = INUSE;
        deleteByGenre(&userplaylist,genre);
        sem_post(&userplaylistLock);
        userplaylistStatus = OK;

        sem_wait(&playlist1Lock);
        playlist1Status = INUSE;
        deleteByGenre(&playlist1,genre);
        sem_post(&playlist1Lock);
        playlist1Status = OK;

        sem_wait(&playlist2Lock);
        playlist2Status = INUSE;
        deleteByGenre(&playlist2,genre);
        sem_post(&playlist2Lock);
        playlist2Status = OK;

        printf("thread B(%d) finished\n",threadID);
    }
    /* THREAD C */
    else if(threadID == 4){

        printf("thread C(%d) started\n",threadID);

        /* gets the lock deletes that is before a certain year and release the lock */
        /* always follows userplaylist, playlist1, playlist2 sequence */

        sem_wait(&userplaylistLock);
        userplaylistStatus = INUSE;
        deleteByYear(&userplaylist,year);
        sem_post(&userplaylistLock);
        userplaylistStatus = OK;

        sem_wait(&playlist1Lock);
        playlist1Status = INUSE;
        deleteByYear(&playlist1,year);
        sem_post(&playlist1Lock);
        playlist1Status = OK;

        sem_wait(&playlist2Lock);
        playlist2Status = INUSE;
        deleteByYear(&playlist2,year);
        sem_post(&playlist2Lock);
        playlist2Status = OK;

        printf("thread C(%d) finished\n",threadID);
    }
    /* THREAD A */
    else{
        /* in loop until both playlist1 and playlist2 is empty */
        while (playlist1Count != 0 || playlist2Count != 0){
            int listNum = getTypeALock();
            /* if list number is not -1 start Task A */
            if(listNum != -1){

                printf("thread A(%d) started with list %d\n", threadID, listNum);

                /* generate a random number between 1 and 10 */
                int cutTime = rand() % 10 + 1;
                /* if playlist1 is not empty and list number is 1 use playlist1 */
                if(listNum == 1 && playlist1Count != 0){
                    /* if random number exceeds the list count make it list count */
                    if(cutTime > playlist1Count){
                        cutTime = playlist1Count;
                    }
                    /* delete the first item from list and paste to userplaylist */
                    /* decrease the count every time */
                    for (int i = 0; i < cutTime; ++i) {
                        Song *temp = delete(&playlist1);
                        insert(&userplaylist,temp, sizeof(Song));
                        playlist1Count--;
                    }
                }
                /* if playlist2 is not empty and list number is 2 use playlist1 */
                if(listNum == 2 && playlist2Count != 0){
                    /* if random number exceeds the list count make it list count */
                    if(cutTime > playlist2Count){
                        cutTime = playlist2Count;
                    }
                    /* delete the first item from list and paste to userplaylist */
                    /* decrease the count every time */
                    for (int i = 0; i < cutTime; ++i) {
                        Song *temp = delete(&playlist2);
                        insert(&userplaylist,temp, sizeof(Song));
                        playlist2Count--;
                    }
                }
                printf("thread A(%d) finished with list %d\n", threadID, listNum);

                /* release the locks */
                releaseTypeALock(listNum);
            }
            /* sleep 1 second for the purpose of prevention of starvation */
            sleep(1);
        }
    }
    return 0;
}

int main(){

    /* initialise random method */
    time_t t;
    srand((unsigned) time(&t));

    /* initialise semaphores */
    sem_init(&playlist1Lock, 0, 1);
    sem_init(&playlist2Lock, 0, 1);
    sem_init(&userplaylistLock, 0, 1);

    /* fill the playlist1 and playlist2 with songs */
    unsigned song_size = sizeof(Song);
    Song* temp = NULL;

    temp = createSong("song1","genre1",1990);
    insert(&playlist1, temp, song_size);
    temp = createSong("song2","genre2",1992);
    insert(&playlist1, temp, song_size);
    temp = createSong("song3","genre3",1993);
    insert(&playlist1, temp, song_size);
    temp = createSong("song4","genre2",1999);
    insert(&playlist1, temp, song_size);
    temp = createSong("song5","genre4",1992);
    insert(&playlist1, temp, song_size);
    temp = createSong("song6","genre3",1992);
    insert(&playlist1, temp, song_size);
    temp = createSong("song7","genre1",2019);
    insert(&playlist1, temp, song_size);
    temp = createSong("song8","genre2",2018);
    insert(&playlist1, temp, song_size);
    temp = createSong("song9","genre4",2018);
    insert(&playlist1, temp, song_size);
    temp = createSong("song10","genre3",1990);
    insert(&playlist1, temp, song_size);

    temp = createSong("song11","genre3",1995);
    insert(&playlist2, temp, song_size);
    temp = createSong("song12","genre4",1991);
    insert(&playlist2, temp, song_size);
    temp = createSong("song13","genre2",1992);
    insert(&playlist2, temp, song_size);
    temp = createSong("song14","genre1",1997);
    insert(&playlist2, temp, song_size);
    temp = createSong("song15","genre4",2019);
    insert(&playlist2, temp, song_size);
    temp = createSong("song16","genre2",1991);
    insert(&playlist2, temp, song_size);
    temp = createSong("song17","genre2",1991);
    insert(&playlist2, temp, song_size);
    temp = createSong("song18","genre3",1999);
    insert(&playlist2, temp, song_size);
    temp = createSong("song19","genre4",2018);
    insert(&playlist2, temp, song_size);
    temp = createSong("song20","genre1",1993);
    insert(&playlist2, temp, song_size);

    playlist1Count = 10;
    playlist2Count = 10;


    /* get genre and year from console */
    printf("Enter the genre:\n");
    if (scanf(" %s", genre) == 0) {
        printf("You entered NaN.\n");
    }

    printf("Enter the year:\n");
    if (scanf(" %d", &year) == 0) {
        printf("You entered NaN.\n");
        year = 0;
    }

    pthread_t tid[5];
    pthread_attr_t attr[5];

    int i=0;

    /* create and initialize threads */
    for(i=0; i<5; i++) {
        void *memory = malloc(sizeof(int));
        int* id = (int *) memory;
        *id = i;
        pthread_attr_init(&attr[i]);
        pthread_create(&tid[i], &attr[i], threads, id);
    }

    /* join the threads */
    for(i=0; i<5; i++) {
        pthread_join(tid[i], NULL);
    }

    /* print the final state of lists */
    printf("playlist1:\n");
    printList(&playlist1);
    printf("playlist2:\n");
    printList(&playlist2);
    printf("userplaylist:\n");
    printList(&userplaylist);


}
