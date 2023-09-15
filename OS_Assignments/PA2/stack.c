#include"stack.h"

//allocates memory for datas.
Stack* createStack(int capacity){

	Stack *newStack = malloc(sizeof(Stack));
    newStack->top = 0;            
    newStack->capacity = capacity;            
    newStack->task = (int*)malloc(capacity*sizeof(int));      
    newStack->values = (int*)malloc(capacity*sizeof(int));            

    return newStack;
}

//checks whether top is equal to the capacity or not.
int isFull(Stack* stack){
	if(stack->top == stack->capacity){
		return 1;
	}else{
		return 0;
	}
}

//checks whether top is 0 or not.
int isEmpty(Stack* stack){
	if(stack->top == 0){
		return 1;
	}else{
		return 0;
	}
}

//checks if stack is full if its full gives warning,
//else adds values to the top.
void push(Stack* stack, int t, int item){
	
	if (isFull(stack)){
		printf("Stack is full.\n");
	}
	else{
		int top = stack->top;
		stack->task[top] = t;
		stack->values[top] = item;
		stack->top = top+1;
	}
}

//checks is empty if it is empty returns -1,
//else return index of the top.s
int pop(Stack* stack){
	if (isEmpty(stack)){
		printf("Stack is empty.\n");
		return -1;
	}
	else{
		int top = stack->top - 1;
		stack->top = top;
		return top;
	}
}