package com.example.rana_2207094_hall_management_system_android;

public class Notice {
    private String title;
    private String message;
    private long date;

    public Notice() {
    }

    public Notice(String title, String message, long date) {
        this.title = title;
        this.message = message;
        this.date = date;
    }

    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public long getDate() { return date; }
}