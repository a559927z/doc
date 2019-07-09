package net.chinahrd.core.tools.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Collections工具集.
 * Collection Factory 方便集合构建的工厂类<p>
 * 省略泛型new的过程，同时在可能的情况下 尽可能使用更优越的实现类
 * 
 */
public class CollectionKit {
    
	/**
     * 构建一个通用的 {@link HashMap} .
     */
    public static <K, V> Map<K, V> newMap() {
        return new HashMap<K, V>();
    }

    public static <K, V> MapList<K, V> newMapList() {
        return new MapList<K, V>();
    }

    /**
     * Constructs and returns a generic {@link java.util.HashSet} creating.
     */
    public static <T> Set<T> newSet() {
        return new HashSet<T>();
    }

    /**
     * Contructs a new {@link HashSet} and initializes it using the provided collection.
     */
    public static <T, V extends T> Set<T> newSet(Collection<V> values) {
        return new HashSet<T>(values);
    }
    
    public static <T, V extends T> Set<T> newSet(V... values) {
        // Was a call to newSet(), but Sun JDK can't handle that. Fucking generics.
        return new HashSet<T>(Arrays.asList(values));
    }

    /**
     * Constructs a new {@link java.util.HashMap} creating by copying an existing Map creating.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <K, V> Map<K, V> newMap(Map<? extends K, ? extends V> map) {
        Map ret = new HashMap<K, V>(map.size());
        ret.putAll(map);
        return ret;
    }

    /**
     * Constructs a new concurrent map, which is safe to access via multiple threads.
     */
    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return new ConcurrentHashMap<K, V>();
    }

    /**
     * Contructs and returns a new generic {@link java.util.ArrayList} creating.
     */
    public static <T> List<T> newList() {
        return new ArrayList<T>();    
    }

    /**
     * Creates a new, fully modifiable list from an initial set of elements.
     */
    public static <T, V extends T> List<T> newList(V... elements) {
        // Was call to newList(), but Sun JDK can't handle that.
        return new ArrayList<T>(Arrays.asList(elements));
    }

    /**
     * Useful for queues.
     */
    public static <T> LinkedList<T> newLinkedList() {
        return new LinkedList<T>();
    }

    /**
     * Constructs and returns a new {@link java.util.ArrayList} as a copy of the provided collection.
     */
    public static <T, V extends T> List<T> newList(Collection<V> list) {
        return new ArrayList<T>(list);    	
    }

    /**
     * Constructs and returns a new {@link java.util.concurrent.CopyOnWriteArrayList}.
     */
    public static <T> List<T> newThreadSafeList() {
        return new CopyOnWriteArrayList<T>();
    }
    
	/**
	 * 提取集合中的对象的两个属性(通过Getter函数), 组合成Map.
	 * 
	 * @param collection 来源集合.
	 * @param keyPropertyName 要提取为Map中的Key值的属性名.
	 * @param valuePropertyName 要提取为Map中的Value值的属性名.
	 */
	public static Map extractToMap(final Collection collection, final String keyPropertyName,
			final String valuePropertyName) {
		Map map = new HashMap(collection.size());

		try {
			for (Object obj : collection) {
				map.put(PropertyUtils.getProperty(obj, keyPropertyName),
						PropertyUtils.getProperty(obj, valuePropertyName));
			}
		} catch (Exception e) {
			throw Reflections.convertReflectionExceptionToUnchecked(e);
		}

		return map;
	}

	/**
	 * 提取集合中的对象的一个属性(通过Getter函数), 组合成List.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 */
	public static <T> List<T> extractToList(final Collection collection, final String propertyName) {
		List<T> list = new ArrayList<T>(collection.size());

		try {
			for (Object obj : collection) {
				list.add((T) PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw Reflections.convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * 提取集合中的对象的一个属性(通过Getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 */
	public static String extractToString(final Collection collection, final String propertyName, final String separator) {
		List list = extractToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 转换Collection所有元素(通过toString())为String, 中间以 separator分隔。
	 */
	public static String convertToString(final Collection collection, final String separator) {
		return StringUtils.join(collection, separator);
	}

	/**
	 * 转换Collection所有元素(通过toString())为String, 每个元素的前面加入prefix，后面加入postfix，如<div>mymessage</div>。
	 */
	public static String convertToString(final Collection collection, final String prefix, final String postfix) {
		StringBuilder builder = new StringBuilder();
		for (Object o : collection) {
			builder.append(prefix).append(o).append(postfix);
		}
		return builder.toString();
	}
	
	
	/**
	 * 将以逗号分隔的字符串转换成集合
	 * @param str
	 * @return
	 */
	public static List<String> strToList(String str){
		return strToList(str,",");
	}
	/**
	 * 将以逗号分隔的字符串转换成集合
	 * @param str
	 * @return
	 */
	public static List<String> strToList(String str, String separator){
		if(StringUtils.isEmpty(str)){
			return Collections.emptyList();
		}
		return Arrays.asList(StringUtils.split(str,separator));
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(Collection collection) {
		return (collection == null) || collection.isEmpty();
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(Map map) {
		return (map == null) || map.isEmpty();
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isNotEmpty(Collection collection) {
		return (collection != null) && !(collection.isEmpty());
	}

	/**
	 * 取得Collection的第一个元素，如果collection为空返回null.
	 */
	public static <T> T getFirst(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		return collection.iterator().next();
	}

	/**
	 * 获取Collection的最后一个元素 ，如果collection为空返回null.
	 */
	public static <T> T getLast(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		// 当类型为List时，直接取得最后一个元素 。
		if (collection instanceof List) {
			List<T> list = (List<T>) collection;
			return list.get(list.size() - 1);
		}

		// 其他类型通过iterator滚动到最后一个元素.
		Iterator<T> iterator = collection.iterator();
		while (true) {
			T current = iterator.next();
			if (!iterator.hasNext()) {
				return current;
			}
		}
	}

	/**
	 * 返回a+b的新List.
	 */
	public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
		List<T> result = new ArrayList<T>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * 返回a-b的新List.
	 */
	public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
		List<T> list = new ArrayList<T>(a);
		for (T element : b) {
			list.remove(element);
		}

		return list;
	}

	/**
	 * 返回a与b的交集的新List.
	 */
	public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
		List<T> list = new ArrayList<T>();
		
		for (T element : a) {
			if (b.contains(element)) {
				list.add(element);
			}
		}
		return list;
	}
	
	/**
	 * Map按值排序 by jxzhang on 2016-02-26
	 * @param oriMap
	 * @return
	 */
	public static Map<String, Integer> sortMapByValue(Map<String, Integer> oriMap) {
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		if (oriMap != null && !oriMap.isEmpty()) {
			//先将待排序oriMap中的所有元素置于一个列表中
			List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(oriMap.entrySet());
			Collections.sort(entryList,
					new Comparator<Map.Entry<String, Integer>>() {
				
						@Override
						public int compare(
								Entry<String, Integer> entry1,
								Entry<String, Integer> entry2) {
							int value1 = 0, value2 = 0;
							try {
								value1 = entry1.getValue();
								value2 = entry2.getValue();
							} catch (NumberFormatException e) {
								value1 = 0;
								value2 = 0;
							}
							return value2 - value1;
						}
					});
			
			Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
			Map.Entry<String, Integer> tmpEntry = null;
			while (iter.hasNext()) {
				tmpEntry = iter.next();
				sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
			}
		}
		return sortedMap;
	}
	
	/**
	 * 拆分集合（指定有多少个集合）	 by jxzhang on 2016-03-06
	 * @param <T>
	 * @param list	原集合
	 * @param itemSize	拆分集合各个拆合大小
	 * 
	 * list.size = 10	itemSize = 3 <br/>
	 * 结果： <br/>
	 * Map<1, 3.size()> <br/>
	 * Map<2, 3.size()> <br/>
	 * Map<3, 3.size()> <br/>
	 * Map<4, 1.size()> <br/>
	 * 
	 * @return
	 */
	public static <T> Map<Integer,List<T>> splitList(List<T> list, int itemSize){
		Map<Integer,List<T>> rsMap = new LinkedHashMap<Integer, List<T>>();
		
		int count = list.size() / itemSize;
		int yu = list.size() % itemSize;
		int last = itemSize -1;
		
		for (int i = 0; i < itemSize; i++) {
			List<T> subList = new ArrayList<T>();
			// 最后一次把乘下的（余）加上去
			if (i == last) {
				subList = list.subList(i * count, count * (i + 1) + yu);
			} else {
				subList = list.subList(i * count, count * (i + 1));
			}
			rsMap.put(i, subList);
		}
		return rsMap;
	}
	
	/**
	 * 拆分集合（指定每集合大少）	 by jxzhang on 2016-03-21
	 * @param list
	 * @param sizeMax
	 * @return
	 */
	public static <T> Map<Integer, List<T>> splitList2(List<T> list,
			int sizeMax) {
		Map<Integer, List<T>> rsMap = new LinkedHashMap<Integer, List<T>>();

		int count = list.size();
		int arrSize = count % sizeMax == 0
				? count / sizeMax
				: count / sizeMax + 1;

		List<T> subList = CollectionKit.newList();

		int startIndex = 0;
		int endIndex = sizeMax;

		for (int i = 1; i <= arrSize; i++) {
			if (i == arrSize) {
				subList = list.subList(startIndex, count);
			} else {
				subList = list.subList(startIndex, endIndex);
				startIndex = endIndex;
				endIndex = startIndex + sizeMax;
			}
			rsMap.put(i, subList);
		}

		return rsMap;
	}
	
}
