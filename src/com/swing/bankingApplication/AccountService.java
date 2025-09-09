package com.swing.bankingApplication;

import java.util.*;

public class AccountService {

    private List<Account> accounts;
    private List<TransactionRequest> requests;
    private List<Transaction> transactions;

    public AccountService() {
        accounts = FileUtil.readAccounts();
        requests = FileUtil.readRequests();
        transactions = FileUtil.readTransactions();
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public Account findByUsername(String username) {
        return accounts.stream().filter(a -> a.getUsername().equals(username)).findFirst().orElse(null);
    }

    public void addAccount(Account account) {
        accounts.add(account);
        FileUtil.writeAccounts(accounts);
    }

    public void deleteByUsername(String username) {
        accounts.removeIf(a -> a.getUsername().equals(username));
        FileUtil.writeAccounts(accounts);
    }

    public boolean deposit(String username, double amount) {
        Account acc = findByUsername(username);
        if (acc != null) {
            acc.setBalance(acc.getBalance() + amount);
            FileUtil.writeAccounts(accounts);
            logTransaction(username, "DEPOSIT", amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(String username, double amount) {
        Account acc = findByUsername(username);
        if (acc != null && acc.getBalance() >= amount) {
            acc.setBalance(acc.getBalance() - amount);
            FileUtil.writeAccounts(accounts);
            logTransaction(username, "WITHDRAW", amount);
            return true;
        }
        return false;
    }

    public List<TransactionRequest> getRequests() {
        return requests;
    }

    public void removeRequest(TransactionRequest req) {
        requests.remove(req);
        FileUtil.writeRequests(requests);
    }

    public void addRequest(TransactionRequest req) {
        requests.add(req);
        FileUtil.writeRequests(requests);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    private void logTransaction(String username, String type, double amount) {
        transactions.add(new Transaction(username, type, amount, new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())));
        FileUtil.writeTransactions(transactions);
    }
}
