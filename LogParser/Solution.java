package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Solution {
    public static void main(String[] args) throws ParseException {
        LogParser logParser = new LogParser(Paths.get("C:\\Users\\dusol\\Desktop\\JavaRushTasks\\JavaRushTasks\\4.JavaCollections\\src\\com\\javarush\\task\\task39\\task3913\\logs"));

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//        String dateString1 = "30.08.2012 16:08:40";
//        String dateString2 = "11.12.2013 10:11:12";
//
//        Date date1 = simpleDateFormat.parse(dateString1);
//        Date date2 = simpleDateFormat.parse(dateString2);
//
//        System.out.println(logParser.getDateWhenUserLoggedFirstTime("Eduard Petrovich Morozko", null, null));
       // System.out.println(logParser.execute("get event for date = \"30.01.2014 12:56:22\""));
        System.out.println(logParser.execute("get ip for user = \"Eduard Petrovich Morozko\" and date between \"13.09.2013 5:04:50\" and \"12.12.2013 21:56:30\""));
    }
}