#include<stdlib.h>

struct song {
    char name [25];
    char genre [15];
    int year;
};
typedef struct song Song;

Song* createSong(char name [25], char genre [15], int year);
void printSong(Song* s);