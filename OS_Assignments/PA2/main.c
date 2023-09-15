#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"binary_search_tree.h"
#include"stack.h"


int main(){

	//rand method init.
	int i;
   	time_t t;
	srand((unsigned) time(&t));

	//initiliasion of tree.
	Node *newNode = createNode(5);
	Node *root = newNode;

    insert(root, 3); 
    insert(root, 2); 
    insert(root, 4); 
    insert(root, 7); 
    insert(root, 6); 
    insert(root, 8);
    
	//initiliasion of stack.
    Stack* stack = createStack(10);

    printf("Initial tree\n");
	inorder(root);
	printf("\n\n");

	for( i = 0 ; i < 10 ; i++ ) {
		//generate a random task.
		int task = rand() % 4 +1;

		if(task == 1){
			//insert method and corresponding output.
			Node *temp = insert(root, i);

		    if(temp!=NULL){
		    	printf("The value %d is inserted\n",i);
				push(stack,task,i);
				inorder(root);
				printf("\n\n");
		    }

		    else{
		    	printf("The value %d is not inserted\n\n",i);
		    }
		}

		else if(task == 2){
			//delete method and corresponding output.
		    Node *temp = deleteNode(root,i);

		    if(temp!=NULL){
		    	printf("The value %d is deleted\n",i);
		    	push(stack,task,i);
				inorder(root);
				printf("\n\n");
		    }

		    else{
		    	printf("The value %d is not deleted\n\n",i);
		    }
			
		}

		else if(task == 3){
			//prints the min value
			printf("Minimum value is %d\n\n", minValueNode(root)->data);
		}

		else{
			//search method and corresponding output.
			int step = search(root,i);
			
			if(step < 0){
				printf("Value %d cannot be found\n\n",i);
			}

			else{
				printf("Value %d found in %d step\n\n",i,step);
			}
		}
   	}

   	//undoing everything.
   	int undone = stack->top;
   	while(undone != 0){

   		int steps = 0;
   		printf("There are %d many steps left.\n",undone);
   		printf("How many steps do you want to back: ");
   		
   		//checks if it is int value if not undone all.
        if (scanf(" %d", &steps) == 0) {
			steps = undone;
        }

        //checks if value is greater than steps to undone if not undone all.
   		if (steps > undone){
   			steps = undone;
   		}
   		
   		for( i = 0 ; i < steps ; i++ ) {
   			int top = pop(stack);
			int task = stack->task[top];
			int value = stack->values[top];
			if(task == 2){
				printf("The value %d is inserted\n",value);
				root = insert(root, value);
				inorder(root);
				printf("\n\n");
			}
			else{
				printf("The value %d is deleted\n",value);
				deleteNode(root,value);
				inorder(root);
				printf("\n\n");
			}
			undone--;
		}
   	}

    return 0;
}