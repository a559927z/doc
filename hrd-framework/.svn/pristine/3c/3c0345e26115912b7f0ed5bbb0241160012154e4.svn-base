package net.chinahrd.core.tools.collection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Map from a given key to a list of values
 * 
 */
public class MapList<K, V> extends HashMap<K, List<V>> {
	private static final long serialVersionUID = 1L;

	public void add(K key, V value) {
		List<V> values = get(key);
		if (values == null) {
			values = new LinkedList<V>();
			put(key, values);
		}
		values.add(value);
	}

	// 名称冲突: net.chinahrd.core.tools.collection.MapList中的remove(K,V)
	// by jxzhang on 2017-04-19
	// public void remove(K key, V value) {
	// List<V> values = get(key);
	// if (values != null) {
	// values.remove(value);
	// }
	// }

	public List<V> list(K key) {
		return super.get(key);
	}

	public V first(K key) {
		List<V> list = list(key);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
