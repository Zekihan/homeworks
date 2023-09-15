#include <string.h>
#include "linkedList.h"

/* allocate space and fill its attribute */
Node* createNode(Song *value, size_t data_size){
    Node *newNode = malloc(sizeof(Node));
    newNode->data  = malloc(data_size);
    newNode->data = value;
    newNode->next = NULL;

    return newNode;
}

/* takes head reference, new data and data's size */
void insert(Node** head_ref, Song *new_data, size_t data_size)
{
    /* create new node */
    Node* new_node = createNode(new_data, data_size);

    /* if list is empty make first one new node */
    if(*head_ref==NULL){
        *head_ref = new_node;
        return;
    }
    /* else go to last node and link its to new node */
    else{
        Node *nextNode = (*head_ref);
        while (nextNode->next != NULL){
            nextNode = nextNode->next;
        }
        nextNode->next = new_node;
    }

}

void printList(Node **start)
{
    /* traverse the list and print */
    if(*start == NULL){
        return;
    }
    Node* node = *start;
    while (node != NULL&&node->data != NULL)
    {

        printSong(node->data);
        node = node->next;
    }
}

Song* delete(Node **start){
    /* if first element is null return null else return first elements data */
    if(*start == NULL){
        return NULL;
    }
    Node* node = *start;
    *start = node->next;
    return node->data;
}
void deleteByGenre(Node **start,char genre[25]){
    Node* node = *start;
    /* count is here for checking if its the first element */
    int count = 0;
    Node* prev = NULL;
    /* iterate through the list and check if they match the genre */
    while (node != NULL)
    {

        if(strcmp(node->data->genre,genre) == 0){
            /* if its first element make second one first */
            if (count == 0){
                *start = node->next;
            }else{
                /* if not link previous one with next one */
                prev->next = node->next;
            }
        }else{
            /* if it is not a match make previous variable current node */
            prev = node;
        }
        node = node->next;
        count++;
    }
}

void deleteByYear(Node **start,int year){
    Node* node = *start;

    /* count is here for checking if its the first element */
    int count = 0;
    Node* prev = NULL;
    /* iterate through the list and check if they match the genre */
    while (node != NULL)
    {

        if(node->data->year < year){
            if (count == 0){
                /* if its first element make second one first */
                *start = node->next;
            }else{
                /* if not link previous one with next one */
                prev->next = node->next;
            }
        }else{
            /* if it is not a match make previous variable current node */
            prev = node;
            count++;
        }
        node = node->next;

    }
}
