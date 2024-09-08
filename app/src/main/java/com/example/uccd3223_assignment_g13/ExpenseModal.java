package com.example.uccd3223_assignment_g13;

public class ExpenseModal {
    private String Amount;
    private String Category;
    private String Description;
    private String Date;
    private int id;

    String getAmount(){return Amount;}

    public void setAmount(String Amount){
        this.Amount = Amount;
    }

    String getCategory(){return Category;}

    public void setCategory(String Category){
        this.Category = Category;
    }

    String getDescription(){return Description;}

    public void setDescription(String Description){
        this.Description = Description;
    }

    String getDate(){return Date;}

    public void setDate(String Date){
        this.Date = Date;
    }

    public int getId() { return  id; }

    public void setId(int id) {this.id = id; }

    public ExpenseModal(String Amount, String Category, String Description, String Date){

        this.Amount = Amount;
        this.Category = Category;
        this.Description = Description;
        this.Date = Date;
    }
}
