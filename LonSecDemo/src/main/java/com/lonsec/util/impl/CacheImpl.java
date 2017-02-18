package com.lonsec.util.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.lonsec.util.Cache;

public class CacheImpl<K, V> implements Cache<K, V> {
	
	private static final int DEFAULT_SIZE = 25;
	private LRUCache<K, V> cache = null;
	
	public CacheImpl(int cacheSize) {
		if(cacheSize > 0)
			cache = new LRUCache<K, V>(cacheSize);
		else
			cache = new LRUCache<K, V>(DEFAULT_SIZE);
	}

	public synchronized void put(K key, V value) {
		cache.put(key, value);
	}

	public V get(K key) {
		return cache.get(key);
	}
	
	@Override
	public String toString() {
		return cache.toString();
	}
}

@SuppressWarnings("serial")
class LRUCache<K,V> extends LinkedHashMap<K, V> {
	private int cacheSize;

	  public LRUCache(int cacheSize) {
	    super(cacheSize, 1, true);
	    this.cacheSize = cacheSize;
	  }

	  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
	    return size() > cacheSize;
	  }
}
