package com.stalern.util;

import org.apache.tomcat.util.res.StringManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Map的子类，防止用户修改参数值
 * @author stalern
 * @date 2019/11/05~16:25
 */
public class ParameterMap<K, V> extends HashMap<K, V> {
    public ParameterMap() {
        super();
    }
    public ParameterMap(int initialCapacity) {
        super(initialCapacity);
    }
    public ParameterMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    private boolean locked = false;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    private static final StringManager sm = StringManager.getManager("com.stalern.util");

    public void clear() {
        if (locked) {
            throw new IllegalStateException(sm.getString("parameterMap.locked"));
        }
        super.clear();
    }

    public V put (K key, V value) {
        if (locked) {
            throw new IllegalStateException(sm.getString("parameterMap.locked"));
        }
        return super.put(key, value);
    }

    public void putAll(Map<? extends K,? extends V> map) {
        if (locked) {
            throw new IllegalStateException(sm.getString("parameterMap.locked"));
        }
        super.putAll(map);
    }

    @Override
    public V remove(Object key) {
        if (locked) {
            throw new IllegalStateException(sm.getString("parameterMap.locked"));
        }
        return super.remove(key);
    }
}
