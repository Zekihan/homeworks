
#include<stdlib.h>
#include"binary_search_tree.h"

//allocates memory for datas,
//childs are left as NULL.
Node* createNode(int value){
    Node *newNode = malloc(sizeof(Node));
    newNode->data = value;            
    newNode->left = NULL;
    newNode->right = NULL;
    
    return newNode;
}

//traverses the tree by inorder,
//first left then root then right child.
void inorder(Node *root){
	if(root == NULL){
		return;
	}
    inorder(root->left);
    printf("%d ->", root->data);
    inorder(root->right);
}

//if root is NULL creates a node,
//if not finds correct position for data and binds to parent.
Node* insert_helper(Node* root, int data){
    if (root == NULL){
        return createNode(data);
    }
    if (data < root->data){
        root->left  = insert(root->left, data);
    }
    else if (data > root->data){
        root->right = insert(root->right, data);   
    }
 
    return root;
}

//first checkes if that value is already in tree,
//if it is returns NULL,
//else goes to insertion method.
Node* insert(Node* root, int data){
    if(search(root,data)!=-1){
        return NULL;
    }
    else{
        return insert_helper(root,data);
    }
}

//if root is null return -1,
//otherwise traverse tree with binary search until it finds value,
//or get a NULL child.
int search_helper(Node* root, int data,int count){
	if (root == NULL){
		return -1;
	}
	if (data == root->data){
	    return count;
	}
	if (data < root->data){
		count++;
	    return search_helper(root->left, data, count);
	}
	if (data > root->data){
		count++;
	    return search_helper(root->right, data, count);
	}
}

//created for giving count value of 0,
//goes to helper method and returns its return.
int search(Node* root, int data){
	int* count = 0;
	count = search_helper(root,data,count);
	return count;
}

//if root is null returns null,
//else goes to the leftmost child and returns value.
Node * minValueNode(Node* node){
	if(node == NULL){
		return NULL;
	}
	if(node->left == NULL){
		return node;
	}
	else{
		return minValueNode(node->left);
	}
}

//traverses until it finds the node,
Node* deleteNode_helper(Node* root, int data){
    if (root == NULL){
        return root; 
    }
    if (data < root->data) {
        root->left = deleteNode(root->left, data); 
    }
    else if (data > root->data){ 
        root->right = deleteNode(root->right, data); 
    }
    else
    {
        //node with only one child or no child,
        //just replaces with child.
        if (root->left == NULL) 
        { 
            Node *temp = root->right;
            free(root); 
            return temp; 
        } 
        else if (root->right == NULL) 
        { 
            Node *temp = root->left; 
            free(root); 
            return temp; 
        }
        //node with two children,
        //Get the inorder successor (smallest in the right subtree).
        Node* temp = minValueNode(root->right); 

        root->data = temp->data; 

        root->right = deleteNode(root->right, temp->data); 
    } 
    return root; 
}

//first checkes if that value is in tree,
//if it is goes to deletion method,
//else returns NULL.
Node* deleteNode(Node* root, int data){
    if(search(root,data)==-1){
        return NULL;
    }
    else{
        return deleteNode_helper(root,data);
    }
}
