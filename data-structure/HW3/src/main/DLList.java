package main;

public class DLList<T> implements ListInterface<T> {
	
	private DLNode firstNode;
	private DLNode lastNode;
	private int size; 
	
	private class DLNode 
	{
		private T data;
		private DLNode next;
		private DLNode prev;
 
		private DLNode(T dataPortion) {
			this(null, dataPortion, null);
		}
		private DLNode(DLNode prev, T dataPortion) {
			this(prev, dataPortion, null);
		}
		private DLNode(T dataPortion, DLNode next) {
			this(null, dataPortion, next);
		}
		
        private DLNode(DLNode prev, T dataPortion, DLNode next) {
            this.data = dataPortion;
            this.next = next;
            this.prev = prev;
        }
        private T getData() {
        	return data;
        }
        private void setData(T newData) {
        	data = newData;
        }
        private DLNode getNextNode() {
        	return next;
        }
        private DLNode getPrevNode() {
        	return prev;
        }
        private void setNextNode(DLNode nextNode) {
        	next = nextNode;
        }
        private void setPrevNode(DLNode prevNode) {
        	prev = prevNode;
        }
        
	}
	
	public DLList() {
		initializeDataFields();
	}
	public void clear() {
		initializeDataFields();
	}
	private void initializeDataFields() {
		firstNode = null;
		lastNode = null;
		size = 0;
	}
	private DLNode getNodeAt(int givenPosition) throws IllegalArgumentException{
		if (!(0<=givenPosition && givenPosition<size()+1)) 
		{
			throw new IllegalArgumentException("Index out Of Bonds");
		}
		else {
			DLNode newNode = firstNode;
		    if (givenPosition < size / 2) 
		    {
		    	newNode = firstNode;
		      for (int i = 1; i < givenPosition; i++) 
		      {
		    	  newNode = newNode.next;
		      }
		    } else {
		      newNode = lastNode;
		      for (int i = size; i > givenPosition; i--) 
		      {
		        newNode = newNode.prev;
		      }
		    }
	
			return newNode;
			}
	    
	}
	public boolean isEmpty() {
		boolean result = false;
		if (size == 0)
			result = true;
		return result;
	}
	public void add(T newEntry) {
		DLNode newNode = new DLNode(newEntry);
	      if( isEmpty() )               
	    	  firstNode = newNode;            
	      else
	         {
	    	  DLNode lastNode2 = lastNode;
	    	  lastNode.next = newNode;        
	    	  newNode.prev = lastNode2;    
	         }
	      lastNode = newNode; 
		
		size++;
	}
	public void add(T newEntry,int newPosition) throws IllegalArgumentException{
		if (!(0<=newPosition && newPosition<size()+1)) 
		{
			throw new IllegalArgumentException("Index out Of Bonds");
		}
		else
		{
			if(newPosition == 1) 
			{
			DLNode newNode = new DLNode(newEntry, firstNode);
			newNode.next = firstNode;
            newNode.prev = null;
            firstNode = newNode;
			}
		else if (newPosition == size) 
		{
			add(newEntry);
		}
		else 
		{
			
			DLNode afterNode = getNodeAt(newPosition);
			DLNode previousNode = afterNode.getPrevNode();
			DLNode newNode = new DLNode(previousNode, newEntry, afterNode);
			previousNode.setNextNode(newNode);
			afterNode.setPrevNode(newNode);
			
		}
		}
	}
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] result = (T[])new Object[size];
		int i = 0 ;
		DLNode currentNode = firstNode;
		while ((i<size)&&(currentNode != null)) {
			result[i] = currentNode.getData();
			currentNode = currentNode.getNextNode();
			i++;
		}
		return result;
	}
	public T remove(int givenPosition) throws IllegalArgumentException{
		if (!(0<=givenPosition && givenPosition<size()+1)) 
		{
			throw new IllegalArgumentException("Index out Of Bonds");
		}
		else 
		{
		T result = null;
		if (givenPosition == 1) {
			result = firstNode.getData();
			firstNode = firstNode.getNextNode();
			
       
		}
		else if (givenPosition == size) 
		{
			result = lastNode.getData();
			lastNode = lastNode.getPrevNode();
			lastNode.setNextNode(null);
	        
		}
		else 
		{
			DLNode nodeToRemove = getNodeAt(givenPosition);
			nodeToRemove.prev.next = nodeToRemove.next;
			nodeToRemove.next.prev = nodeToRemove.prev;
		}
		size--;
		return result;
		}
	}
	
	public T replace(int givenPosition, T newEntry) throws IllegalArgumentException{
		if (!(0<=givenPosition && givenPosition<size()+1)) 
		{
			throw new IllegalArgumentException("Index out Of Bonds");
		}
		else{
		DLNode nodeToReplace = getNodeAt(givenPosition);
	
		T originalEntry = nodeToReplace.getData();
		nodeToReplace.setData(newEntry);
		return originalEntry;
	}
	}
	public T getEntry(int givenPosition) throws IllegalArgumentException{
		if (!(0<=givenPosition && givenPosition<size()+1)) 
		{
			throw new IllegalArgumentException("Index out Of Bonds");
		}
		else{
			return getNodeAt(givenPosition).getData();
		}
	}
	public boolean contains (T anEntry) {
		boolean found = false;
		DLNode currentNode = firstNode;
		while ((!found)&&(currentNode != null)) {
			if (anEntry.equals(currentNode.getData())) {
				found = true;
			}
			else {
				currentNode = currentNode.getNextNode();
			}
		}
		return found;
	}
	public String toString(){
        DLNode cn = firstNode;
        String str = "";
        while(cn != null){
                str += cn.getData();
                //System.out.print(cn.getC());
                cn = cn.getNextNode();
        }
        
        return str;
}
	public int size() {
		return size;
	}
	
}
