package com.example.rana_2207094_hall_management_system_android;

public class HallBill {
    private String month;
    private int amount;

    public HallBill() {
    }

    public HallBill(String month, int amount) {
        this.month = month;
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public int getAmount() {
        return amount;
    }
}