package com.javarush.task.task39.task3913;

import java.util.Date;

public class LogLine {
    private String ip;
    private String user;
    private Date date;
    private Event event;
    private Integer taskNumber;
    private Status status;

    public LogLine(String ip, String user, Date date, String event, String status) {
        this.ip = ip;
        this.user = user;
        this.date = date;

        String[] eventStatus = event.split(" "); //splitting status
        switch (eventStatus[0]){
            case "LOGIN":
                this.event = Event.LOGIN;
                break;
            case "DOWNLOAD_PLUGIN":
                this.event =Event.DOWNLOAD_PLUGIN;
                break;
            case "WRITE_MESSAGE":
                this.event =Event.WRITE_MESSAGE;
                break;
            case "SOLVE_TASK":
                this.event =Event.SOLVE_TASK;
                this.taskNumber = Integer.parseInt(eventStatus[1]);
                break;
            case "DONE_TASK":
                this.event =Event.DONE_TASK;
                this.taskNumber = Integer.parseInt(eventStatus[1]);
                break;
        }

        switch (status){
            case "OK":
                this.status = Status.OK;
                break;
            case "FAILED":
                this.status = Status.FAILED;
                break;
            case "ERROR":
                this.status = Status.ERROR;
                break;
        }
    }

    //Univeral getter
    @SuppressWarnings("unchecked")
    public <T> T getField(String field){
        switch (field){
            case "ip": return (T) ip;
            case "user": return (T) user;
            case "date": return (T) date;
            case "event": return (T) event;
            case "tasknumber": return (T) taskNumber;
            case "status": return (T) status;
        }
        return null;
    }

    public String getIp() {
        return ip;
    }

    public String getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public Event getEvent() {
        return event;
    }

    public Integer getTaskNumber() {
        return taskNumber;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "LogLine{" +
                "ip='" + ip + '\'' +
                ", user='" + user + '\'' +
                ", date=" + date +
                ", event=" + event +
                ", taskNumber=" + taskNumber +
                ", status=" + status +
                '}';
    }
}
