package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        int count = 0;
        for (Map.Entry<K, List<V>> entry : map.entrySet()){
            count += entry.getValue().size();
        }
       return count;
    }

    @Override
    public V put(K key, V value) {
        if (map.containsKey(key)) {
            List<V> list = map.get(key);
            if (list.isEmpty()){
                list.add(value);
              return null;
            }else{
                if(list.size()<repeatCount){
                    V last = list.get(list.size()-1);
                    list.add(value);
                    return last;
                }else {
                    V last = list.get(list.size()-1);
                    list.remove(0);
                    list.add(value);
                    return last;
                }
            }
        }else {
            List<V> list = new ArrayList<>();
            list.add(value);
            map.put(key, list);
            return null;
        }
    }

    @Override
    public V remove(Object key) {
        if (map.containsKey(key)){
            List<V> list = map.get(key);
            if (!list.isEmpty()){
                V last = list.remove(0);
                if (list.isEmpty()){
                    map.remove(key);
                }
                return last;
            } else {
                map.remove(key);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        Collection<V> list = new ArrayList<>();
        for (Map.Entry<K, List<V>> entry : map.entrySet()){
            list.addAll(entry.getValue());
        }
        return list;
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (Map.Entry<K, List<V>> entry : map.entrySet()){
            if (entry.getValue().contains(value)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}