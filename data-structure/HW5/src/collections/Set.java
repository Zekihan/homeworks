package collections;

public interface Set<T> {
	
	/**
	 * Adds a new entry to this set, avoiding duplicates.
	 * @param element The object to be added as a new entry.
	 */
	public void add(T element);
	/**
	 * Removes a specific entry from this set, if possible.
	 * @param element The entry to be removed.
	 */
	public void remove(T element);
	/**
	 * Removes one unspecified entry from this set, if possible.
	 */
	public void remove();
	/**
	 * Tests whether this set contains a given entry.
	 * @param element The entry to locate.
	 * @return True if the set contains anEntry, or false if not
	 */
	public boolean contains(T element);
	/**
	 * Gets the current number of entries in this set.
	 * @return The integer number of entries currently in the set.
	 */
	public int size();
	/**
	 * Sees whether this set is empty.
	 * @return  True if the set is empty, or false if not. 
	 * */
	public boolean isEmpty();
	/**
	 * Retrieves all entries that are in this set.
	 * @return A newly allocated array of all the entries in the set.
	 */
	public T[] toArray();
	
}
