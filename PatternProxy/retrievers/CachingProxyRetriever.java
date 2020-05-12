package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.cache.LRUCache;
import com.javarush.task.task37.task3708.storage.Storage;

public class CachingProxyRetriever implements Retriever {
    private OriginalRetriever retriever;
    private LRUCache<Long, Object> cache = new LRUCache<>(100);

    public CachingProxyRetriever(Storage storage) {
        this.retriever = new OriginalRetriever(storage);
    }

    @Override
    public Object retrieve(long id) {
        if (cache.find(id) != null){
            return cache.find(id);
        } else {
            Object ob = retriever.retrieve(id);
            cache.set(id, ob);
            return ob;
        }
    }
}
