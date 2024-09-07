package com.example.uccd3223_assignment_g13;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Expense")
public class Expense {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "amount")
    private double amount;
    @ColumnInfo(name = "desc")
    private String desc;
    @ColumnInfo(name = "date")
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


    public double getAmount() {
        return amount;
    }


    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }
}
