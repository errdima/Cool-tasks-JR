package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;

public class FunctionalTest {
    public void testStorage(Shortener shortener){
        String s1 = "Test1 string  ";
        String s2 = "Test2 string";
        String s3 = "Test1 string  ";

        Long key1 = shortener.getId(s1);
        Long key2 = shortener.getId(s2);
        Long key3 = shortener.getId(s3);

        Assert.assertNotEquals(s2, s1);
        Assert.assertNotEquals(s2, s3);
        Assert.assertEquals(s1, s3);

        String s1result = shortener.getString(key1);
        String s2result = shortener.getString(key2);
        String s3result = shortener.getString(key3);

        Assert.assertEquals(s1result, s1);
        Assert.assertEquals(s2result, s2);
        Assert.assertEquals(s3result, s3);
    }
    @Test
    public void testHashMapStorageStrategy(){
        Shortener shortener = new Shortener(new HashMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testOurHashMapStorageStrategy(){
        Shortener shortener = new Shortener(new OurHashMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testFileStorageStrategy(){
        Shortener shortener = new Shortener(new FileStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testHashBiMapStorageStrategy(){
        Shortener shortener = new Shortener(new HashBiMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testDualHashBidiMapStorageStrategy(){
        Shortener shortener = new Shortener(new DualHashBidiMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testOurHashBiMapStorageStrategy(){
        Shortener shortener = new Shortener(new OurHashBiMapStorageStrategy());
        testStorage(shortener);
    }
}
