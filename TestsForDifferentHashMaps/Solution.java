package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static Set<Long> getIds(Shortener shortener, Set<String> strings){
        Set<Long> ids = new HashSet<>();
        for (String s : strings){
            ids.add(shortener.getId(s));
        }
        return ids;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys){
        Set<String> strings = new HashSet<>();
        for (Long id: keys){
            strings.add(shortener.getString(id));
        }
        return strings;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber){
        System.out.println(strategy.getClass().getSimpleName());
        //Генерируем тестовое множество строк
        Set<String> testStringSet = new HashSet<>();
        for (int i = 0; i<elementsNumber; i++){
            testStringSet.add(Helper.generateRandomString());
        }
        //Вводим Shortener с переданной стратегией
        Shortener shortener = new Shortener(strategy);
        //Замер времени отработки метода getIds для заданной стратегии и заданного множества элементов
        Date startTimeTest1 = new Date();
        Set<Long> ids = getIds(shortener, testStringSet);
        Date stopTimeTest1 = new Date();
        System.out.println(stopTimeTest1.getTime() - startTimeTest1.getTime());
        //Замер времени отработки метода getStrings для заданной стратегии и полученного Set ids
        Date startTimeTest2 = new Date();
        Set<String> strings = getStrings(shortener, ids);
        Date stopTimeTest2 = new Date();
        System.out.println(stopTimeTest2.getTime() - startTimeTest2.getTime());
        //Сравниваем testStringSet c strings
        if (testStringSet.equals(strings)){
            System.out.println("Тест пройден.");
        }else {
            System.out.println("Тест не пройден.");
        }
    }
    public static void main(String[] args) {
        testStrategy(new HashMapStorageStrategy(), 10000);
        testStrategy(new OurHashMapStorageStrategy(), 10000);
        //testStrategy(new FileStorageStrategy(), 50);
        testStrategy(new OurHashBiMapStorageStrategy(), 10000);
        testStrategy(new HashBiMapStorageStrategy(), 10000);
        testStrategy(new DualHashBidiMapStorageStrategy(), 10000);
    }
}
