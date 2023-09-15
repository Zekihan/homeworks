#include<stdlib.h>
#include <string.h>
#include <stdio.h>
#include "song.h"

/* create song */
Song* createSong(char name [25], char genre [15], int year){
    Song *newSong = malloc(sizeof(Song));
    strcpy(newSong->name,name);
    strcpy(newSong->genre,genre);
    newSong->year = year;
    return newSong;
}

/* print song */
void printSong(Song* s){
    printf("Name: %s\t", s->name);
    printf("Genre: %s\t", s->genre);
    printf("Year: %d\n", s->year);
}