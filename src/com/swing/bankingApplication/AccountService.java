package com.swing.bankingApplication;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private static final List<Account> ACCOUNTS = new ArrayList<>();

    static {
        ACCOUNTS.add(new Account("Smith",  "Smith123",  "Smith Georges",  "Male",   "Savings",  5000.00, true));
        ACCOUNTS.add(new Account("Raju", "Raju123", "Rajesh Sharma","Male","Current", 12000.50,false));
    }

    public synchronized void addAccount(Account acc) {
        ACCOUNTS.add(acc);
    }

    public synchronized List<Account> getAllAccounts() {
        return new ArrayList<>(ACCOUNTS);
    }

    public synchronized Account findByUsername(String username) {
        for (Account a : ACCOUNTS) {
            if (a.getUsername().equals(username)) return a;
        }
        return null;
    }

    public synchronized void deleteByUsername(String username) {
        ACCOUNTS.removeIf(a -> a.getUsername().equals(username));
    }
}
