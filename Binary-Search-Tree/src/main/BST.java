package main;

import java.util.List;

public interface BST<K extends Comparable<K>,V> {
	/**
	 * Returns the value associated with ​key​.
	 * @return value, if value with given key do not exist returns null
	 */
	public V get(K key);
	/**
	 * Adds ​value​ to the tree with respect to ​key​.
	 */
	public void add(K key, V value);
	/**
	 * Removes the value associated with ​key​.
	 * @param key
	 */
	public void remove(K key);
	/**
	 * Updates the value associated with key​ from old value to value​
	 * @param key
	 * @param value
	 */
	public void update(K key, V value);
	/**
	 * Returns all the keys in the tree.
	 * @return list of keys
	 */
	public List<K> keys();
	/**
	 * Returns all the values in the tree.
	 * @return list of values
	 */
	public List<V> values();
	/**
	 * Returns the value associated with the smallest key.
	 * @return minimum value
	 */
	public V min();
	/**
	 * Returns the value associated with the largest key.
	 * @return maximum value
	 */
	public V max();
	/**
	 * Returns all the values associated with a smaller key than ​key​.
	 * @return list of values
	 */
	public List<V> lessThan(K key);
	/**
	 * Returns all the values associated with a larger key than ​key​.
	 * @return list of values
	 */
	public List<V> greaterThan(K key);
	/**
	 * Returns the number of values (or keys) in the tree.
	 * @return an integer of size
	 */
	public int size();
	/**
	 * Returns the maximum number of values that appear between the
root and any leaf (root and leaf are counted as well)
	 * @return an integer of height
	 */
	public int height();
	/**
	 * Prints the tree to the console.
	 */
	public void display();
	/**
	 * Determines whether the tree is full (it is full if and only if every
node other than leaves has exactly two children and all the leaves are
on the same level)
	 * @return boolean
	 */
	public boolean isFullTree();
	
}	

	
