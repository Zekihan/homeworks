package collections;

public interface MinHeap<T extends Comparable<? super T>> {
	

	/**
	 * Adds a new entry to this minheap.
	 * @param newEntry An object of the new entry.
	 */
	public void add(T newEntry);
	/**
	 * Removes the minimum element from this minheap.
	 * @return The minimum element.
	 */
	public T removeMin();
	/**
	 * Retrieves the minimum element from this minheap.
	 * @return The minimum element.
	 */
	public T getMin();
	/**
	 * Sees whether this minheap is empty.
	 * @return True if the minheap is empty.
	 */
	public boolean isEmpty();
	/**
	 * Gets the size of this minheap.
	 * @return The number of entries currently in the minheap.
	 */
	public int getSize();
	/**
	 * Removes all entries from this minheap
	 */
	public void clear();
	
}
