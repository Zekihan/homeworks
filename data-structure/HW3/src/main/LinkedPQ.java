package main;

public class LinkedPQ<T> implements PriorityQueueInterface<T> {

	private Node<T> headNode;
    private int size;
    
	public void add(T newEntry, int priority) {
		
		Node<T> node = headNode;
		headNode = insert(node, newEntry, priority);
	}
	
	public T remove() {
		
		if(null == headNode) 
		{
			return null;
	    }
        T data = headNode.data;
        headNode = headNode.next;
        size--;
        return data;
	    }

	
	public T peek() {
		
		if(null == headNode) 
			return null;
		
		T data = headNode.data;
		return data;
	}

	
	public boolean isEmpty() {
		
		if (headNode != null)
			return false;
		
		else
			return true;
	}
	
	public int getSize() {
		return size;
	}

	
	public void clear() {
		headNode = null;
		
	}
	
	private Node<T> insert(Node<T> node, T data, int priority) {
		
		Node<T> newNode =  createNewNode(data,priority);
		
		if(node == null) 
		{
			return newNode;
		}
		if(node.priority < priority) 
		{
			newNode.next = node;
			node = newNode;
	    }
		else 
	    {
			node.next = insert(node.next, data, priority);
			size--;
	    }
		
	    return node;
	    
	}
	
	private Node<T> createNewNode(T data, int priority) {
		size++;
		return new Node<T>(data, priority);
		}
	
	private static class Node<T>
	{
		private Node<T> next;
		private final T data;
		private final int priority;
		
		public Node(T data, int priority)
		{
		this.data = data;
		this.priority = priority;
		}
	}

}

