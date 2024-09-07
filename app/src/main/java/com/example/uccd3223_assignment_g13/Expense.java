package com.example.uccd3223_assignment_g13;

public class Expense {

    private int id;

    private String category;
    private double amount;
    private String desc;
    private String date;

    public Expense(double amount, String category, String desc, String date) {
        this.category = category;
        this.amount = amount;
        this.desc = desc;
        this.date = date;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
