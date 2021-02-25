package utils.testlink;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Returns the results from the TestLINK API methods. It standardizes the results as a list of Maps. This is done since as of version 1.8.2 of the TestLink API mostly returns a list of Maps. When this
 * is not done then a key of Result count is assigned by the result list and TestLink API result object is returned as a map value.
 * 
 */
public class TestLinkAPIResults implements TestLinkAPIConst {
	ArrayList <Map <String, Object>> results = new ArrayList <Map <String, Object>>();

	/**
	 * Add a result to the list.
	 * 
	 * @param item
	 *            : Map String and Object
	 */
	public void add(Map <String, Object> item) {
		// Inspect the item first. If it is a map of maps
		// then get the individual hashes.
		if (isMapOfMaps(item)) {
			Iterator <String> keys = item.keySet().iterator();
			while (keys.hasNext()) {
				Object key = keys.next();
				@SuppressWarnings("unchecked")
				Map <String, Object> innerMap = (Map <String, Object>) item.get(key);
				if (innerMap != null) {
					results.add(innerMap);
				}
			}
		}
		else {
			results.add(item);
		}
	}

	/**
	 * Remove a result from the list
	 * 
	 * @param index
	 *            : Index
	 */
	public void remove(int index) {
		results.remove(index);
	}

	/**
	 * Get the result data in the list
	 * 
	 * @param index
	 *            : Index
	 * @return: Map of String and Object
	 */
	public Map <String, Object> getData(int index) {
		return results.get(index);
	}

	/**
	 * Get the values within the result data by name
	 * 
	 * @param index
	 *            : Index
	 * @param name
	 *            : Name
	 * @return: Object
	 */
	public Object getValueByName(int index, String name) {
		Map <String, Object> result = getData(index);
		return getValueByName(result, name);
	}

	/**
	 * Recurse the structure for the results.
	 * 
	 * @param result
	 *            : Result
	 * @param name
	 *            : Name
	 * @return: Object
	 */
	public Object getValueByName(Map <String, Object> result, String name) {
		Object value = result.get(name);
		if (value == null) {
			Iterator <String> mapKeys = result.keySet().iterator();
			while (mapKeys.hasNext()) {
				Object internalKey = mapKeys.next();
				Object internalData = result.get(internalKey);
				if (internalData == null) {
					continue;
				}
				if (internalData instanceof Map) {
					@SuppressWarnings("unchecked")
					Map <String, Object> internalMap = (Map <String, Object>) internalData;
					value = internalMap.get(name);
					if (value != null) {
						return value;
					}
					else {
						getValueByName(internalMap, name);
					}
				}
			}
		}
		return value;
	}

	/**
	 * Is Map of Maps
	 * 
	 * @param map
	 *            " Map of String and Object
	 * @return: Boolean
	 */
	private boolean isMapOfMaps(Map <String, Object> map) {
		Iterator <String> keys = map.keySet().iterator();
		while (keys.hasNext()) {
			Object key = keys.next();
			Object data = map.get(key);
			if (data == null) {
				continue;
			}
			else if (data instanceof Map) {
				continue;
			}
			return false;
		}
		return true;
	}

	/**
	 * Get the size of the results list.
	 * 
	 * @return: Size count
	 */
	public int size() {
		return results.size();
	}

	@Override
	public String toString() {
		if (results.size() == 0) { return "The results list is empty."; }
		StringBuffer value = new StringBuffer();
		for (int i = 0; i < results.size(); i++) {
			Object m = results.get(i);
			if (m != null) {
				value.append("Result[" + i + "] = " + m.toString() + "\n");
			}
		}
		return value.toString();
	}
}
