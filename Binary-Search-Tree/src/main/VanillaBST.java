package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class VanillaBST<K extends Comparable<K>, V> implements BST<K, V> {

	
	private Node<K,V>[] nodeArray;
	private int size;
	private int maxIndex;
	
	@SuppressWarnings("unchecked")
	public VanillaBST() {
		nodeArray = (Node<K,V>[])new Node[1];
		maxIndex = 1;
	}
	@SuppressWarnings("unchecked")
	public VanillaBST(List<K> keyList, List<V> valueList) {
		if(keyList.size() == valueList.size()) 
		{
			Node<K,V>[] tmpArray = (Node<K,V>[])new Node[keyList.size()];
			nodeArray = (Node<K,V>[])new Node[1];
			maxIndex = 1;
			for(int i=0; i<keyList.size();i++)
			{
				K key = keyList.get(i);
				V value = valueList.get(i);
				Node<K,V> newNode = new Node<K, V>(key, value);
				tmpArray[i] = newNode;
				
			}
			insertionSort(tmpArray);
			balancedAdd(tmpArray);
			
		}
		else 
		{
			throw new IllegalArgumentException("Key list and Value list does not match");
		}
	}
	private void balancedAdd(Node<K,V>[] arr) {
		int n = arr.length;
		int mid = n/2;

		
		add(arr[mid].getKey(),arr[mid].getValue());
		
		Node<K,V>[] left = Arrays.copyOfRange(arr, 0, mid);
		Node<K,V>[] right = Arrays.copyOfRange(arr, mid+1, n);

		if  (left.length > 0 )
		{
			
			balancedAdd(left);
			
		}
		if (right.length > 0) {
			balancedAdd(right);
		}
	
 }
	
	private void insertionSort(Node<K,V>[] arr)
	{
	   int i, j;
	   Node<K,V> node;
	   
	   for (i = 1; i < arr.length; i++)
	   {
	       node = arr[i];
	       j = i-1;

	       while (j >= 0 && arr[j].getKey().compareTo(node.getKey()) > 0)
	       {
	           arr[j+1] = arr[j];
	           j = j-1;
	       }
	       arr[j+1] = node;
	   }
	}
	
		
	private int getNodeIndex(K key) {
		int currentIndex = 0;
		boolean found = false;
		while (!found)
        {	
			if(nodeArray[currentIndex] != null)
			{
			
				if (key.compareTo((nodeArray[currentIndex].getKey()) ) == 0)
				{
					found = true;
				}
				else if (key.compareTo((nodeArray[currentIndex].getKey()) ) < 0) 
	            {
	                 currentIndex = currentIndex*2+1;
	            }
	            else 
	            {
	                 currentIndex = currentIndex*2+2;
	            }
			
			}
			else {
				currentIndex = -1;
				break;
			}
			
        }
		return currentIndex;
	}
	public V get(K key) {
		int targetIndex = getNodeIndex(key);
		if (targetIndex != -1)
			return nodeArray[targetIndex].getValue();
		else
			return null;
	}
	

	public void add(K key,V value) {
		
		if (nodeArray.length < maxIndex*2+3)
		{
			nodeArray = Arrays.copyOf(nodeArray,maxIndex*2+3);
		}
		
		
		Node<K,V> newNode = new Node<K, V>(key, value);
		int currentIndex = 0;
		boolean added = false;
        while (!added)
        {
        	
        	if (maxIndex == 1)
        	{
        	nodeArray[currentIndex] = newNode;
        	added = true;
        	maxIndex += 1; 
            }
        	
        	else if (key.compareTo((nodeArray[currentIndex].getKey()) ) < 0) {
              if (nodeArray[currentIndex*2+1] == null) {
                 nodeArray[currentIndex*2+1] = newNode;
                 added = true;
                 if (currentIndex*2+1 > maxIndex)
                     maxIndex = currentIndex*2+1;
              }
              else
                 currentIndex = currentIndex*2+1;
           }
           else 
           {
              if (nodeArray[currentIndex*2+2] == null) 
              {
                 nodeArray[currentIndex*2+2] = newNode;
                 added = true;
                 if (currentIndex*2+2 > maxIndex)
                     maxIndex = currentIndex*2+2;
              }
              else
                 currentIndex = currentIndex*2+2;
           }
           
        }
        size++;	
	}
	
	public void remove(K key) {
		
		int targetIndex = getNodeIndex(key);
		
		if (targetIndex != -1)
		{
			SearchRemove(key,targetIndex);
		}
		else
		{
			throw new NoSuchElementException("There is no element with given key");
		}
		
	}
	
	
	private void remove(int targetIndex) {
 
	
		// if its a leaf
		if (isLeaf(targetIndex))
		{
			nodeArray[targetIndex] = null;
			size--;
		}

		// if node only has left child
		else if ((nodeArray[targetIndex*2+1] != null) && (nodeArray[targetIndex*2+2] == null)) 
		{
	       nodeArray[targetIndex] = nodeArray[targetIndex*2+1];
		}
		// if node only has right child
		else if ((nodeArray[targetIndex*2+1] == null) && (nodeArray[targetIndex*2+2] != null)) 
		{
			targetIndex = 2*targetIndex+2;
			size--;
		}
		// if node has two children
		else
		{
			int index = SearchInorderSuccessor(targetIndex*2+2);
			nodeArray[targetIndex] = nodeArray[index];
			
			if (nodeArray[index*2+2] != null)
			{
				nodeArray[index] = nodeArray[index*2+2];
				nodeArray[index*2+2] = null;
			}
			
			if (isLeaf(index))
			{
				nodeArray[index] = null;
			}
			
			size--;
			
		}				
	}
		
	private boolean SearchRemove(K key, int targetIndex){
		
		if(nodeArray[targetIndex] != null && targetIndex <= maxIndex)
		{
			if(key.compareTo((nodeArray[targetIndex].getKey()) ) == 0)
			{
				remove(targetIndex);
				return true;
			}
			else if(key.compareTo((nodeArray[targetIndex].getKey()) ) < 0)
			{
				targetIndex = targetIndex*2 + 1;
				SearchRemove(key, targetIndex);
			}
			else
			{
				targetIndex = targetIndex*2 + 2;
				SearchRemove(key, targetIndex);
			}
		}
		
		return false;
	}
	
	
	private int SearchInorderSuccessor(int targetIndex)
	{
		if (nodeArray[targetIndex*2+1] != null)
		{
			targetIndex = targetIndex*2+1;
			
			if (isLeaf(targetIndex*2+1))
			{
				return targetIndex;
			}
			
			return SearchInorderSuccessor(targetIndex);
		}
		else
		{
			return targetIndex;
		}
		
	}

	private boolean isLeaf(int targetIndex) 
	{
		if ((targetIndex*2+1 >= nodeArray.length) || (targetIndex*2+2 >= nodeArray.length))
		{
			return true;
		
		}
		else if ((nodeArray[targetIndex*2+1] == null) && (nodeArray[targetIndex*2+2] == null))
		{    
			return true;
		}
		else
		{
			return false;
		}
	}

	public void update(K key, V value) {
		int targetIndex = getNodeIndex(key);
		Node<K, V> newNode = nodeArray[targetIndex];
		newNode.setValue(value);
		
	}

	
	public List<K> keys() {
		
		List<K> keyList = new ArrayList<K>();
		
		for(int i = 0; i < nodeArray.length; i++) 
		{
			if (nodeArray[i] != null)
			{
				keyList.add(nodeArray[i].getKey());
			}
		}
			
		return keyList;
	}

	
	public List<V> values() {

		List<V> valueList = new ArrayList<V>();
		
		for(int i = 0; i < nodeArray.length; i++) 
		{
			if (nodeArray[i] != null)
			{
				valueList.add(nodeArray[i].getValue());
			}	
		}
		
		return valueList;	
	}
	

	
	public V min() {
		int currentIndex = 0;
		Node<K,V> currentNode = nodeArray[currentIndex];
		V value = currentNode.getValue();
		
		while (currentNode != null)
        {	
			value = currentNode.getValue();
			currentIndex = currentIndex*2+1;
			currentNode = nodeArray[currentIndex];
        }

		return value;
	}

	
	public V max() {
		
		int currentIndex = 0;
		Node<K,V> currentNode = nodeArray[currentIndex];
		V value = currentNode.getValue();
		
		while (currentNode != null)
        {
			value = currentNode.getValue();
			currentIndex = currentIndex*2+2;
			currentNode = nodeArray[currentIndex];
        }

		return value;
	}

	
	public List<V> lessThan(K key) {
		ArrayList<V> less = new ArrayList<V>();
		int targetIndex = getNodeIndex(key), side = leftOrRight(targetIndex);
		if (targetIndex != -1)
		{	
			if (side == 1)
			{
				ArrayList<V> lessLeft = new ArrayList<V>() , lessParent = new ArrayList<V>();
				lessLeft = leftLesser(targetIndex,0);
				lessParent = parentLesser(targetIndex);
				less.addAll(lessLeft);
				less.addAll(lessParent);
				
			}
			else
			{
				ArrayList<V> lessLeft = new ArrayList<V>();
				
				lessLeft = leftLesser(targetIndex,0);
				less.addAll(lessLeft);
			}
		}
		else
		{
			throw new NoSuchElementException("There is no element with given key");
		}
		
		return less;
	}
	private int leftOrRight(int index) {
		if (index == 0) {
			return 0;
		}
		else if ((index%2) == 1) {
			return -1;
		}
		else {
			return 1;
		}
	}
	private ArrayList<V> leftLesser(int index,int time) {
		ArrayList<V> less = new ArrayList<V>(),lessLeft = new ArrayList<V>(),lessRight = new ArrayList<V>();
		int currentIndex = index*2+1;
		time++;
		if (nodeArray[currentIndex] != null) {
			less.add(nodeArray[currentIndex].getValue());
		}
		if ((time != 1)&&(nodeArray[currentIndex+1] != null)) {
			less.add(nodeArray[currentIndex+1].getValue());
		}
		
		if (nodeArray[currentIndex] != null) {
					
			if (nodeArray[currentIndex*2+1] != null) {
				lessLeft.add(nodeArray[currentIndex*2+1].getValue());
				less.addAll(lessLeft);
				less.addAll(leftLesser(currentIndex*2+1,time));
			}
			if (nodeArray[currentIndex*2+2] != null) {
				lessRight.add(nodeArray[currentIndex*2+2].getValue());
				less.addAll(lessRight);
				less.addAll(leftLesser(currentIndex*2+2,time));
			}
			
		}
		
		return less;
	}
	private int getParent(int index) {
		int parent ;
		if (index == 0){
			parent = -1;
		}
		else {
			parent = index/2;
		}
		return parent;
		
	}
	private ArrayList<V> parentLesser(int index) {
		ArrayList<V> less = new ArrayList<V>(),lessLeft = new ArrayList<V>(),lessRight = new ArrayList<V>();
		int currentIndex = getParent(index);
		if ((currentIndex <= maxIndex)&&(-1 < currentIndex )) {
			if (currentIndex == 0) {
				less.add(nodeArray[0].getValue());
				lessLeft = leftLesser(0,0);
				less.addAll(lessLeft);
				less.addAll(lessRight);
			}
			else if (nodeArray[currentIndex] != null) {
				
				lessRight = leftLesser(currentIndex,0);
				lessRight.add(nodeArray[currentIndex].getValue());
				less.addAll(lessRight);
				less.addAll(parentLesser(currentIndex));
			}
		} 
		return less;
	}
	
	
	public List<V> greaterThan(K key) {
		ArrayList<V> greater = new ArrayList<V>();
		int targetIndex = getNodeIndex(key), side = leftOrRight(targetIndex);
		if (targetIndex != -1)
		{	
			if (side == -1)
			{
				ArrayList<V> greaterRight = new ArrayList<V>() , greaterParent = new ArrayList<V>();
				greaterRight = rightGreater(targetIndex,0);
				greaterParent = parentGreater(targetIndex);
				greater.addAll(greaterRight);
				greater.addAll(greaterParent);
				
			}
			else
			{
				ArrayList<V> greaterRight = new ArrayList<V>();
				
				greaterRight = rightGreater(targetIndex,0);
				greater.addAll(greaterRight);
			}
		}
		else
		{
			throw new NoSuchElementException("There is no element with given key");
		}
		
		return greater;
	}

	private ArrayList<V> parentGreater(int index) {
		ArrayList<V> greater = new ArrayList<V>(), greaterRight = new ArrayList<V>();
		int currentIndex = getParent(index);
		if ((currentIndex <= maxIndex)&&(-1 < currentIndex )) {
			if (currentIndex == 0) {
				greater.add(nodeArray[0].getValue());
				greaterRight = rightGreater(0,0);
				greater.addAll(greaterRight);
			}
			else if (nodeArray[currentIndex] != null) {
				
				greaterRight.addAll(rightGreater(currentIndex,0));
				greaterRight.add(nodeArray[currentIndex].getValue());
				greater.addAll(greaterRight);
				greater.addAll(parentGreater(currentIndex));
			}
		} 
		return greater;
	}
	private ArrayList<V> rightGreater(int index, int time) {
		ArrayList<V> greater = new ArrayList<V>(),greaterLeft = new ArrayList<V>(),greaterRight = new ArrayList<V>();
		int currentIndex = index*2+2;
		time++;
		if ((currentIndex <= maxIndex)&&(-1 < currentIndex )) {
		if (nodeArray[currentIndex] != null) {
			greater.add(nodeArray[currentIndex].getValue());
		}
		if ((time != 1)&&(nodeArray[currentIndex-1] != null)) {
			greater.add(nodeArray[currentIndex-1].getValue());
			
		}
		}
		if((currentIndex < maxIndex)&&(-1 < currentIndex )) {
		if (nodeArray[currentIndex] != null) {
					
			if (nodeArray[currentIndex*2+1] != null) {
				greaterLeft.add(nodeArray[currentIndex*2+1].getValue());
				greater.addAll(greaterLeft);
				greater.addAll(rightGreater(currentIndex*2+1,time));
			}
			if (nodeArray[currentIndex*2+2] != null) {
				greaterRight.add(nodeArray[currentIndex*2+2].getValue());
				greater.addAll(greaterRight);
				greater.addAll(rightGreater(currentIndex*2+2,time));
				
			}
			
		}
		}
		return greater;
	}
	public int size() {

		return size;
	}

	
	public int height() {
		int height;
		if (size==1) {
			height = 1;
		}
		else {
			height = (int)(Math.log(maxIndex + 1) / Math.log(2)) + 1;
		}

		return height;
	}

	public void display() {
		System.out.println("Root: " + nodeArray[0].display());
		
		display(0,0);
		
	}
	private void display(int currentIndex, int k) {
		
		if (!isLeaf(currentIndex)) {
			
			if (nodeArray[currentIndex*2+1] != null) {
				
				
				for (int i=0;i<k;i++) {
					System.out.print("┃  ");
				}
				
				
				
				System.out.print("┣━━");
				System.out.println("Left: " + nodeArray[currentIndex*2+1].display());
				
			}
			else {
				
				for (int i=0;i<k;i++) {
					System.out.print(" ");
				}
					System.out.print("┣━━");
					
				System.out.println("Left: -");
			}
			
			display(currentIndex*2+1,k+1);
			if (nodeArray[currentIndex*2+2] != null) {
				
				
				for (int i=0;i<k;i++) {
					System.out.print("┃  ");
				}
					System.out.print("┗━━");

				System.out.println("Right: " + nodeArray[currentIndex*2+2].display());
				
			}
			else {
				
				for (int i=0;i<k;i++) {
					System.out.print("┃  ");
				}
					System.out.print("┗━━");
	
				System.out.println("Right: -");
			}currentIndex = currentIndex*2+2;
			display(currentIndex,k+1);
		}
		
	}

	
	public boolean isFullTree() {
		boolean isFull = false;
		int total = 0;
		for (int i = 0 ; i < height() ; i++ ) {
			total += Math.pow(2, i);
		}
		if (total == size()) {
			isFull = true;
		}
		
		return isFull;
	}

	@SuppressWarnings("hiding")
	private class Node<K,V> {
		
		private V value;
		private K key;
		
		public Node(K key, V value) 
		{
			this.key = key;
			this.value = value;	
		}
		public K getKey() {
			return key;
		}
		
		public V getValue() {
			return value;
		}
		public void setValue(V desiredValue) {
			this.value = desiredValue;
		}
		public String display() {
			String str = "(" + key + ", " + value + ")";
			return str;
			
		}
		                  
	}
}


