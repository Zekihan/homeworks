#include<stdlib.h>
#include "song.h"

struct node
{
    Song  *data;
    struct node *next;
};
typedef struct node Node;

Node* createNode(Song *value, size_t data_size);
void insert(Node** head_ref, Song *new_data, size_t data_size);
void printList(Node **start);
Song* delete(Node **start);
void deleteByGenre(Node **start,char genre[25]);
void deleteByYear(Node **start,int year);

