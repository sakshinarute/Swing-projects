package com.swing.bankingApplication;

public class Transaction {
    private String username;
    private String type;
    private double amount;
    private String when;

    public Transaction(String username, String type, double amount, String when) {
        this.username = username;
        this.type = type;
        this.amount = amount;
        this.when = when;
    }

    public String getUsername() { return username; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getWhen() { return when; }
}
