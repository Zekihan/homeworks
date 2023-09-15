package collections;

public class ArrayMinHeap<T extends Comparable<? super T>> implements MinHeap<T>
{
	private T[] heap;
	private int lastIndex;
	private static final int DEFAULT_CAPACITY = 20;
	
	public ArrayMinHeap()
	{
		this(DEFAULT_CAPACITY);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayMinHeap(int initialCapacity)
	{
		heap = (T[]) new Comparable[initialCapacity + 1];
		lastIndex = 0;
	}
	
	//copy constructor
	public ArrayMinHeap(ArrayMinHeap<T> another)
	{
		this.heap = another.heap;
		this.lastIndex = another.lastIndex;
	}
	
	
	public void add(T newEntry)
	{
		int newIndex = lastIndex + 1;
		heap[newIndex] = newEntry;
		int parentIndex = newIndex / 2;
		while(parentIndex > 0 && heap[parentIndex].compareTo(newEntry) > 0)
		{
			heap[newIndex] = heap[parentIndex];
			newIndex = parentIndex;
			parentIndex = parentIndex / 2;
		}
		
		heap[newIndex] = newEntry;
		lastIndex++;
		if (lastIndex+1 >= heap.length) 
		{
		    expandArray();
		}
	}
	

	public T removeMin()
	{
		T root = null;
		
		if(!isEmpty())
		{
			root = heap[1];
			heap[1] = heap[lastIndex];
			lastIndex--;
			reheap(1);
		}
		return root;
	}
	
	public T getMin()
	{
		if (!isEmpty())
		{
			return heap[1];
		}
		return null;
		
	}

	public boolean isEmpty()
	{
		if (lastIndex < 0)
		{
			return true;
		}
		return false;
	}
	
	public int getSize()
	{
		return lastIndex;
	}
	
	public void clear()
	{
		for (; lastIndex > -1; lastIndex--) 
		{
			heap[lastIndex] = null;
		}
	}
	
	private void reheap(int rootIndex) 
	{
		boolean done = false;
		T orphan = heap[rootIndex];
		int leftChildIndex = rootIndex * 2;
		
		while(!done && leftChildIndex <= lastIndex)
		{
			int smallerChildIndex = leftChildIndex;
			int rightChildIndex = leftChildIndex + 1;
			
			if((rightChildIndex <= lastIndex) && heap[rightChildIndex].compareTo(heap[leftChildIndex]) < 0 )
			{
				smallerChildIndex = rightChildIndex;
			}
			if(orphan.compareTo(heap[smallerChildIndex]) > 0)
			{
				heap[rootIndex] = heap[smallerChildIndex];
				rootIndex = smallerChildIndex;
				leftChildIndex = rootIndex *2;
			}
			else
			{
				done = true;
			}
		}
		heap[rootIndex] = orphan;
	}	
	
	@SuppressWarnings("unchecked")
	private void expandArray() 
	{
		T[] newArray = (T[]) new Comparable[heap.length * 2];
	    System.arraycopy(heap, 0, newArray, 0, heap.length);

	    heap = newArray;
	}
}


