package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {
    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids){
        Date startTime = new Date();
        for (String st : strings){
            ids.add(shortener.getId(st));
        }
        Date stopTime = new Date();
        return stopTime.getTime() - startTime.getTime();
    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings){
        Date startTime = new Date();
        for (Long id : ids){
            strings.add(shortener.getString(id));
        }
        Date stopTime = new Date();
        return stopTime.getTime() - startTime.getTime();
    }

    @Test
    public void testHashMapStorage(){
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++){
            origStrings.add(Helper.generateRandomString());
        }
        Set<Long> ids = new HashSet<>();

        Long timeForGetIds1 = getTimeToGetIds(shortener1, origStrings, ids);
        Long timeForGetIds2 = getTimeToGetIds(shortener2, origStrings, ids);

        Assert.assertTrue(timeForGetIds1 > timeForGetIds2);

        Long timeForGetStrings1 = getTimeToGetStrings(shortener1, ids, origStrings);
        Long timeForGetStrings2 = getTimeToGetStrings(shortener2, ids, origStrings);

        Assert.assertEquals(timeForGetStrings1, timeForGetStrings2, 30);
    }
}
