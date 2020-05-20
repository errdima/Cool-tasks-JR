package com.javarush.task.task39.task3913.tests;

import org.junit.Assert;
import org.junit.Test;
import com.javarush.task.task39.task3913.*;

import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class LogParserTest {

    static private LogParser logParser;
    static private Date date1;
    static private Date date2;

    static {
        logParser = new LogParser(Paths.get("C:\\Users\\dusol\\Desktop\\JavaRushTasks\\JavaRushTasks\\4.JavaCollections\\src\\com\\javarush\\task\\task39\\task3913\\logs"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String dateString1 = "30.08.2012 16:08:40";
        String dateString2 = "11.12.2013 10:11:12";
        try {
             date1 = simpleDateFormat.parse(dateString1);
             date2 = simpleDateFormat.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNumberOfUniqueIPs() throws ParseException {
        int expected1 = 3;
        int expected2 = 3;
        int expected3 = 5;
        int expected4 = 5;

        int actual1 = logParser.getNumberOfUniqueIPs(date1, date2);
        int actual2 = logParser.getNumberOfUniqueIPs(null, date2);
        int actual3 = logParser.getNumberOfUniqueIPs(date1, null);
        int actual4 = logParser.getNumberOfUniqueIPs(null, null);

        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
    }

    @Test
    public void getUniqueIPs() {
        Set<String> expected1 = new HashSet<>();
        Collections.addAll(expected1, "192.168.100.2", "146.34.15.5", "127.0.0.1");
        Set<String> expected2 = new HashSet<>();
        Collections.addAll(expected2, "192.168.100.2", "146.34.15.5", "127.0.0.1");
        Set<String> expected3 = new HashSet<>();
        Collections.addAll(expected3, "192.168.100.2", "146.34.15.5", "127.0.0.1", "12.12.12.12", "120.120.120.122");
        Set<String> expected4 = new HashSet<>();
        Collections.addAll(expected4, "192.168.100.2", "146.34.15.5", "127.0.0.1", "12.12.12.12", "120.120.120.122");

        Set<String> actual1 = logParser.getUniqueIPs(date1, date2);
        Set<String> actual2 = logParser.getUniqueIPs(null, date2);
        Set<String> actual3 = logParser.getUniqueIPs(date1, null);
        Set<String> actual4 = logParser.getUniqueIPs(null, null);

        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
    }

    @Test
    public void getIPsForUser() {
        Set<String> expected1 = new HashSet<>();
        Set<String> expected2 = new HashSet<>();
        Collections.addAll(expected2, "127.0.0.1");
        Set<String> expected3 = new HashSet<>();
        Collections.addAll(expected3, "12.12.12.12", "120.120.120.122");
        Set<String> expected4 = new HashSet<>();
        Collections.addAll(expected4, "127.0.0.1", "12.12.12.12", "120.120.120.122");

        Set<String> actual1 = logParser.getIPsForUser("Amigo", date1, date2);
        Set<String> actual2 = logParser.getIPsForUser("Amigo",null, date2);
        Set<String> actual3 = logParser.getIPsForUser("Amigo", date1, null);
        Set<String> actual4 = logParser.getIPsForUser("Amigo",null, null);

        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
    }

    @Test
    public void getIPsForEvent() {
        Set<String> expected1 = new HashSet<>();
        Collections.addAll(expected1, "192.168.100.2");
        Set<String> expected2 = new HashSet<>();
        Collections.addAll(expected2, "192.168.100.2");
        Set<String> expected3 = new HashSet<>();
        Collections.addAll(expected3, "146.34.15.5", "192.168.100.2");
        Set<String> expected4 = new HashSet<>();
        Collections.addAll(expected4, "146.34.15.5", "192.168.100.2");

        Set<String> actual1 = logParser.getIPsForEvent(Event.DONE_TASK, date1, date2);
        Set<String> actual2 = logParser.getIPsForEvent(Event.DONE_TASK,null, date2);
        Set<String> actual3 = logParser.getIPsForEvent(Event.DONE_TASK, date1, null);
        Set<String> actual4 = logParser.getIPsForEvent(Event.DONE_TASK,null, null);

        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
    }

    @Test
    public void getIPsForStatus() {
        Set<String> expected1 = new HashSet<>();
        Set<String> expected2 = new HashSet<>();
        Set<String> expected3 = new HashSet<>();
        Collections.addAll(expected3, "192.168.100.2");
        Set<String> expected4 = new HashSet<>();
        Collections.addAll(expected4, "192.168.100.2");

        Set<String> actual1 = logParser.getIPsForStatus(Status.ERROR, date1, date2);
        Set<String> actual2 = logParser.getIPsForStatus(Status.ERROR,null, date2);
        Set<String> actual3 = logParser.getIPsForStatus(Status.ERROR, date1, null);
        Set<String> actual4 = logParser.getIPsForStatus(Status.ERROR,null, null);

        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
    }

    @Test
    public void getNumberOfUserEvents() {
        int expected1 = 2;
        int expected2 = 2;
        int expected3 = 5;
        int expected4 = 5;

        int actual1 = logParser.getNumberOfUserEvents("Eduard Petrovich Morozko", date1, date2);
        int actual2 = logParser.getNumberOfUserEvents("Eduard Petrovich Morozko", null, date2);
        int actual3 = logParser.getNumberOfUserEvents("Eduard Petrovich Morozko", date1, null);
        int actual4 = logParser.getNumberOfUserEvents("Eduard Petrovich Morozko",null, null);

        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
    }

    @Test
    public void testGetDoneTaskUsers() {
        Set<String> expected1 = new HashSet<>();
        Collections.addAll(expected1, "Vasya Pupkin");
        Set<String> expected2 = new HashSet<>();
        Collections.addAll(expected2, "Vasya Pupkin");
        Set<String> expected3 = new HashSet<>();
        Collections.addAll(expected3, "Vasya Pupkin");
        Set<String> expected4 = new HashSet<>();
        Collections.addAll(expected4, "Vasya Pupkin");

        Set<String> actual1 = logParser.getDoneTaskUsers(date1, date2, 15);
        Set<String> actual2 = logParser.getDoneTaskUsers(null, date2, 15);
        Set<String> actual3 = logParser.getDoneTaskUsers(date1, null, 15);
        Set<String> actual4 = logParser.getDoneTaskUsers(null, null, 15);

        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
        Assert.assertEquals(expected4, actual4);
    }
}