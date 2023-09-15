package main;

public interface ListInterface<T> {
	/**
	 * Clears the list
	 * Removing all items
	 */
	public void clear();
	/**
	 * Checking whether list is empty or not
	 * @return true if list is empty
	 */
	public boolean isEmpty();
	/**
	 * Adding a new entry to the list's back
	 * @param newEntry takes a new entry
	 */
	public void add(T newEntry);
	/**
	 * Adding a new Entry to the desired position
	 * @param newEntry takes a new entry
	 * @param newPosition takes desired position
	 */
	public void add(T newEntry,int newPosition);
	/**
	 * Converting list to array
	 * @return An array
	 */
	public T[] toArray();
	/**
	 * Removing an item from desired position
	 * @param givenPosition takes a desired position
	 * @return entry at desired position
	 */
	public T remove(int givenPosition);
	/**
	 * Replacing an entry from desired position
	 * @param givenPosition takes a desired position
	 * @param newEntry takes a new entry
	 * @return old entry at desired position
	 */
	public T replace(int givenPosition, T newEntry);
	/**
	 * Gets an entry without removing from desired position
	 * @param givenPosition takes a desired position
	 * @return entry at desired position
	 */
	public T getEntry(int givenPosition) ;
	/**
	 * Checking whether an entry in list or not
	 * @param anEntry takes an entry
	 * @return true if entry is in the list
	 */
	public boolean contains (T anEntry) ;
	/**
	 * Converting list to string
	 * @return An String
	 */
	public String toString();
	/**
	 * Giving the size of the list
	 * @return An integer of size
	 */
	public int size();
	
	
}
