package com.swing.bankingApplication;


public class Account {
    private String username;    
    private String password;    
    private String ownerName;   
    private String gender;      
    private String accountType; 
    private double balance;
    private boolean smsAlerts;

    public Account(String username, String password, String ownerName, String gender, String accountType, double balance, boolean smsAlerts) {
        this.username = username;
        this.password = password;
        this.ownerName = ownerName;
        this.gender = gender;
        this.accountType = accountType;
        this.balance = balance;
        this.smsAlerts = smsAlerts;
    }

   
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getOwnerName() { return ownerName; }
    public String getGender() { return gender; }
    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }
    public boolean isSmsAlerts() { return smsAlerts; }

    public void setPassword(String password) { this.password = password; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public void setGender(String gender) { this.gender = gender; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
    public void setBalance(double balance) { this.balance = balance; }
    public void setSmsAlerts(boolean smsAlerts) { this.smsAlerts = smsAlerts; }
}


