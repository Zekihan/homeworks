package collections;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ArraySet<T> implements Set<T>{
	
	private T[] set;
	private int size = 0;
	
	
	@SuppressWarnings("unchecked")
	public ArraySet() {
		this.set = (T[]) new Object[1];
		this.size = 0;
	}
	
	//copy constructor
	public ArraySet(ArraySet<T> another)
	{
		this.set = another.set;
		this.size = another.size;
	}

	public void add(T element) {
		ensureCapacity();
		if (!contains(element)) {
			set[size] = element;
			size++;
		}
		
	}

	public void remove(T element) {
		int index = getIndex(element);
		set[index] = set[size-1];
		set[size-1] = null;
		size--;
		
	}
	
	public void remove() {
		set[size-1] = null;
		size--;
	}

	public boolean contains(T element) {
		int index = getIndex(element);
		boolean contain = false;
		if (index != -1) {
			contain = true;
		}
		return contain;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		boolean empty = false;
		if (size == 0) {
			empty = true;
		}
		return empty;
	}

	@SuppressWarnings("unchecked")
	public T[] toArray() {
		T element;
		
		T[] newArray = (T[]) Array.newInstance(getType(), size);
		ArrayList<T> elements = new ArrayList<T>();
		for (int i =0;i<size;i++) {
			element = set[i];
			elements.add(element);
		}
		newArray = elements.toArray(newArray);
		return newArray;
	}
	private int getIndex(T element) {
		int index = -1,i;
		for (i = 0; i < size(); i++) {
			if (set[i] == element){
				index = i;
			}
		}
		return index;
		
	}

	private Class<?> getType() {
		Class<?> type=null;
		if (size != 0){
			type = set[0].getClass();
		}
		return type;
	}
	private void ensureCapacity() {
	        set = Arrays.copyOf(set, set.length+1);
	}
	

	

}

