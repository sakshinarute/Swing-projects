package com.swing.bankingApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private static final String ACCOUNTS_FILE = "accounts.ser";
    private static final String REQUESTS_FILE = "requests.ser";
    private static final String TRANSACTIONS_FILE = "transactions.ser";

    private static List<Account> ACCOUNTS = new ArrayList<>();
    private static List<TransactionRequest> REQUESTS = new ArrayList<>();
    private static List<Transaction> TRANSACTIONS = new ArrayList<>();

    static {
        loadAccounts();
        loadRequests();
        loadTransactions();
        if (ACCOUNTS.isEmpty()) {
            ACCOUNTS.add(new Account("Smith", "Smith123", "Smith Georges", "Male", "Savings", 5000.00, true));
            ACCOUNTS.add(new Account("Raju", "Raju123", "Rajesh Sharma", "Male", "Current", 12000.50, false));
            saveAccounts();
        }
    }

    // --- accounts ---
    public synchronized void addAccount(Account a) {
        ACCOUNTS.add(a);
        saveAccounts();
    }

    public synchronized List<Account> getAllAccounts() {
        return new ArrayList<>(ACCOUNTS);
    }

    public synchronized Account findByUsername(String username) {
        for (Account a : ACCOUNTS) if (a.getUsername().equals(username)) return a;
        return null;
    }

    public synchronized void deleteByUsername(String username) {
        ACCOUNTS.removeIf(a -> a.getUsername().equals(username));
        saveAccounts();
    }

    // --- transactions applied immediately by admin on approve ---
    public synchronized boolean deposit(String username, double amount) {
        if (amount <= 0) return false;
        Account a = findByUsername(username);
        if (a == null) return false;
        a.setBalance(a.getBalance() + amount);
        TRANSACTIONS.add(new Transaction(username, "Deposit", amount));
        saveAccounts();
        saveTransactions();
        return true;
    }

    public synchronized boolean withdraw(String username, double amount) {
        if (amount <= 0) return false;
        Account a = findByUsername(username);
        if (a == null) return false;
        if (a.getBalance() < amount) return false;
        a.setBalance(a.getBalance() - amount);
        TRANSACTIONS.add(new Transaction(username, "Withdraw", amount));
        saveAccounts();
        saveTransactions();
        return true;
    }

    // --- requests ---
    public synchronized void addRequest(TransactionRequest r) {
        REQUESTS.add(r);
        saveRequests();
    }
    public synchronized List<TransactionRequest> getRequests() {
        return new ArrayList<>(REQUESTS);
    }
    public synchronized void removeRequest(TransactionRequest r) {
        REQUESTS.remove(r);
        saveRequests();
    }

    // --- transaction history ---
    public synchronized List<Transaction> getTransactions() {
        return new ArrayList<>(TRANSACTIONS);
    }

    // --- persistence helpers ---
    private static void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ACCOUNTS_FILE))) {
            oos.writeObject(ACCOUNTS);
        } catch (Exception e) { e.printStackTrace(); }
    }
    @SuppressWarnings("unchecked")
    private static void loadAccounts() {
        File f = new File(ACCOUNTS_FILE);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            ACCOUNTS = (List<Account>) ois.readObject();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void saveRequests() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REQUESTS_FILE))) {
            oos.writeObject(REQUESTS);
        } catch (Exception e) { e.printStackTrace(); }
    }
    @SuppressWarnings("unchecked")
    private static void loadRequests() {
        File f = new File(REQUESTS_FILE);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            REQUESTS = (List<TransactionRequest>) ois.readObject();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void saveTransactions() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TRANSACTIONS_FILE))) {
            oos.writeObject(TRANSACTIONS);
        } catch (Exception e) { e.printStackTrace(); }
    }
    @SuppressWarnings("unchecked")
    private static void loadTransactions() {
        File f = new File(TRANSACTIONS_FILE);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            TRANSACTIONS = (List<Transaction>) ois.readObject();
        } catch (Exception e) { e.printStackTrace(); }
    }
}