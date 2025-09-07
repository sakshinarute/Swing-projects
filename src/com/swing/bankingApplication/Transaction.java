package com.swing.bankingApplication;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String username;
    private final String type; 
    private final double amount;
    private final Date when;

    public Transaction(String username, String type, double amount) {
        this.username = username;
        this.type = type;
        this.amount = amount;
        this.when = new Date();
    }

    public String getUsername() { return username; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public Date getWhen() { return when; }
}