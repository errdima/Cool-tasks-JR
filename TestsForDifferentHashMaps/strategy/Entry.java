package com.javarush.task.task33.task3310.strategy;

import java.io.Serializable;
import java.util.Objects;

public class Entry implements Serializable {
    Long key;
    String value;
    Entry next;
    int hash;

    public Entry(int hash, Long key, String value, Entry next) {
        this.key = key;
        this.value = value;
        this.next = next;
        this.hash = hash;
    }

    public Long getKey(){
        return this.key;
    }

    public String getValue(){
        return this.value;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(key, entry.key) &&
                Objects.equals(value, entry.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
//    public int hashCode(){
//        return Objects.hashCode(key) ^ Objects.hashCode(value);
//    }
//
//    public boolean equals(Entry o){
//        if (o == this)
//            return true;
//        return Objects.equals(key, o.getKey()) &&
//                Objects.equals(value, o.getValue());
//    }

    public String toString(){
        return new String(key + "=" + value);
    }
}
