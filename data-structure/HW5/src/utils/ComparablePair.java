package utils;

public class ComparablePair<K extends Comparable<K>, V> 
	implements Comparable<ComparablePair<K, V>>{
	
	private K key;
	private V value;
	
	public ComparablePair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}
	
	public V getValue() {
		return value;
	}

	public int compareTo(ComparablePair<K, V> other) {
		return this.getKey().compareTo(other.getKey());
	}
	
}
