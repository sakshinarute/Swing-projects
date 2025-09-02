package com.swing.bankingApplication;

import java.io.Serializable;

public class TransactionRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String username;
    private final String type; 
    private final double amount;

    public TransactionRequest(String username, String type, double amount) {
        this.username = username;
        this.type = type;
        this.amount = amount;
    }

    public String getUsername() { return username; }
    public String getType() { return type; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return username + " - " + type + " - " + amount;
    }
}
