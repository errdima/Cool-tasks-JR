package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    private Path logDir;

    public LogParser(Path logDir) {
        this.logDir = logDir;
    }
    //читаем в один список все строки из всех файлов
    public List<String> readLog () {
        List<Path> filesToRead;
        List<String> allLines = new ArrayList<>();
        try {
            filesToRead = Files.list(logDir) //сканируем дирректорию на Логи
                    .filter(x -> x.toString().endsWith(".log"))
                    .collect(Collectors.toList());
            for (Path file : filesToRead){
                List<String> tmp = Files.readAllLines(file, Charset.defaultCharset());
                allLines.addAll(tmp);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return allLines;
    }
    //Формируем нужный список объектов LogLine с выборкой по дате
    public Set<LogLine> setOfLogLines(Date after, Date before) {
        Set<LogLine> result = new HashSet<>();
        List<String> log = readLog();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        for (String line : log) {
            String[] data = line.split("\\t");
            Date dateObj = null;
            try {
                dateObj = simpleDateFormat.parse(data[2]);
            } catch (ParseException e){
                System.out.println("Неверная дата, проверьте даты в логах");
            }
            //собираем все строки лога ДО даты (включая)
        if (after == null && before != null) {
                if (dateObj.compareTo(before)<=0){
                    result.add(new LogLine(data[0], data[1], dateObj, data[3], data[4]));
                }
            }
            //собираем все строки лога ПОСЛЕ даты (включая)
        else if (after != null && before == null){
            if (dateObj.compareTo(after)>=0){
                result.add(new LogLine(data[0], data[1], dateObj, data[3], data[4]));
            }
            //собираем ВСЕ строки лога
        } else if (after == null && before ==null){
            result.add(new LogLine(data[0], data[1], dateObj, data[3], data[4]));
            //собираем строки ОТ и ДО даты (включительно)
        } else {
            if (dateObj.compareTo(after)>=0 && dateObj.compareTo(before)<=0){
                result.add(new LogLine(data[0], data[1], dateObj, data[3], data[4]));
                }
            }
        }
        return result;
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        Set<String> strings = new HashSet<>();
        strings = setOfLogLines(after, before).stream()
                .map(LogLine::getIp)
                .collect(Collectors.toSet());

        return strings.size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {

        return setOfLogLines(after, before).stream()
                .map(LogLine::getIp)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {

        return setOfLogLines(after, before).stream()
                .filter(log -> log.getUser().equals(user))
                .map(LogLine::getIp)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getEvent().equals(event))
                .map(LogLine::getIp)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getStatus().equals(status))
                .map(LogLine::getIp)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllUsers() {
        return setOfLogLines(null,null).stream()
                .map(LogLine::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        return setOfLogLines(after,before).stream()
                .map(LogLine::getUser)
                .collect(Collectors.toSet()).size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
         return  setOfLogLines(after, before).stream()
                .filter(log -> log.getUser().equals(user))
                 .map(LogLine::getEvent)
                 .collect(Collectors.toSet()).size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getIp().equals(ip))
                .map(LogLine::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getEvent().equals(Event.LOGIN))
                .map(LogLine::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getEvent().equals(Event.DOWNLOAD_PLUGIN))
                .map(LogLine::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getEvent().equals(Event.WRITE_MESSAGE))
                .map(LogLine::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getEvent().equals(Event.SOLVE_TASK))
                .map(LogLine::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getEvent().equals(Event.SOLVE_TASK))
                .filter(log -> log.getTaskNumber().equals(task))
                .map(LogLine::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getEvent().equals(Event.DONE_TASK))
                .map(LogLine::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {

        return setOfLogLines(after, before).stream()
                .filter(log -> log.getEvent().equals(Event.DONE_TASK))
                .filter(log -> log.getTaskNumber().equals(task))
                .map(LogLine::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getUser().equals(user))
                .filter(log -> log.getEvent().equals(event))
                .map(LogLine::getDate)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getStatus().equals(Status.FAILED))
                .map(LogLine::getDate)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getStatus().equals(Status.ERROR))
                .map(LogLine::getDate)
                .collect(Collectors.toSet());
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getUser().equals(user))
                .filter(log -> log.getEvent().equals(Event.LOGIN))
                .map(LogLine::getDate)
                .sorted()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getUser().equals(user))
                .filter(log -> log.getEvent().equals(Event.SOLVE_TASK))
                .filter(log -> log.getTaskNumber().equals(task))
                .map(LogLine::getDate)
                .sorted()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getUser().equals(user))
                .filter(log -> log.getEvent().equals(Event.DONE_TASK))
                .filter(log -> log.getTaskNumber().equals(task))
                .map(LogLine::getDate)
                .sorted()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getUser().equals(user))
                .filter(log -> log.getEvent().equals(Event.WRITE_MESSAGE))
                .map(LogLine::getDate)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getUser().equals(user))
                .filter(log -> log.getEvent().equals(Event.DOWNLOAD_PLUGIN))
                .map(LogLine::getDate)
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .map(LogLine::getEvent)
                .collect(Collectors.toSet()).size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .map(LogLine::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getIp().equals(ip))
                .map(LogLine::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getUser().equals(user))
                .map(LogLine::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getStatus().equals(Status.FAILED))
                .map(LogLine::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getStatus().equals(Status.ERROR))
                .map(LogLine::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        return (int) setOfLogLines(after, before).stream()
                .filter(log -> log.getEvent().equals(Event.SOLVE_TASK))
                .filter(log -> log.getTaskNumber().equals(task))
                .map(LogLine::getEvent)
                .count();
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        return (int) setOfLogLines(after, before).stream()
                .filter(log -> log.getEvent().equals(Event.DONE_TASK))
                .filter(log -> log.getTaskNumber().equals(task))
                .map(LogLine::getEvent)
                .count();
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getEvent().equals(Event.SOLVE_TASK))
                .collect(Collectors.toMap(
                        LogLine::getTaskNumber, //маппер для ключей
                        log -> 1, //маппер для значений
                        Integer::sum //мерджер, вызывается тогда когда ключ в мапе уже есть,
                ));                    //принимает параметрами предыдущее значение по ключу и новое, которое мы пытаемся по нему замапить.
    }                               //В итоге их можно объеденить

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getEvent().equals(Event.DONE_TASK))
                .collect(Collectors.toMap(
                        LogLine::getTaskNumber,
                        log -> 1,
                        Integer::sum
                ));
    }

    @Override
    public Set<Object> execute(String query) {

        //если совпадает с примитивный запросом
        switch (query){
            case "get ip":
                return new HashSet<>(getUniqueIPs(null, null));
            case "get user":
                return new HashSet<>(getAllUsers());
            case "get date":
                return setOfLogLines(null, null).stream()
                        .map(LogLine::getDate)
                        .collect(Collectors.toSet());
            case "get event":
                return setOfLogLines(null, null).stream()
                        .map(LogLine::getEvent)
                        .collect(Collectors.toSet());
            case "get status":
                return setOfLogLines(null, null).stream()
                        .map(LogLine::getStatus)
                        .collect(Collectors.toSet());
        }
        //если сложный запрос
        String[] request = parseQuery(query); //разбиваем на get field1(0) for field2(1) = "value1"(2) and date between "after"(3) and "before"(4)
        Date after = null;
        Date before = null;
        if (request[3] != null && request[4] !=null) {
            after = new Date(parseDate(request[3]).getTime()+1000);
            before = new Date(parseDate(request[4]).getTime()-1000);
        }
        return setOfLogLines(after, before).stream()
                .filter(log -> log.getField(request[1]).equals(parseValue(request[1], request[2])))
                .map(log -> log.getField(request[0]))
                .collect(Collectors.toSet());
    }
    //парсим и кастим значение к нужному типу
    @SuppressWarnings("unchecked")
    public <T> T parseValue (String type, String value){
        switch (type) {
            case "date":
                return (T) parseDate(value);
            case "event":
                return (T) Event.valueOf(value);
            case "status":
                return (T) Status.valueOf(value);
            default:
                return (T) value;
        }
    }

    public Date parseDate(String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = null;
        if (dateString != null) {
            try {
                date = simpleDateFormat.parse(dateString);
            } catch (ParseException ignored) {
            }
            return date;
        } else
            return null;
    }

    public String[] parseQuery(String query){
        Pattern pattern = Pattern.compile("get (?<field1>\\w+)\\sfor\\s(?<field2>\\w+)\\s=\\s\"(?<value>.+?)\"(\\sand date between\\s\"(?<after>.+)\"\\sand\\s\"(?<before>.+)\")?");
        Matcher matcher = pattern.matcher(query);
        String field1 = "";
        String field2 = "";
        String value = "";
        String after = "";
        String before = "";

        while (matcher.find()) {
            field1 = matcher.group("field1");
            field2 = matcher.group("field2");
            value = matcher.group("value");
            after = matcher.group("after");
            before = matcher.group("before");
        }

        return new String[]{field1, field2, value, after, before};
    }
}