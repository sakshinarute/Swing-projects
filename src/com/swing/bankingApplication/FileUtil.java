	package com.swing.bankingApplication;
	
	import java.io.*;
	import java.util.*;
	
	public class FileUtil {
	
	    private static final String ACCOUNTS_FILE = "accounts.csv";
	    private static final String REQUESTS_FILE = "requests.csv";
	    private static final String TRANSACTIONS_FILE = "transactions.csv";
	
	    // =================== ACCOUNTS ===================
	    public static List<Account> readAccounts() {
	        List<Account> list = new ArrayList<>();
	        File file = new File(ACCOUNTS_FILE);
	        if (!file.exists()) return list;
	
	        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	            String line;
	            br.readLine(); // Skip header
	            while ((line = br.readLine()) != null) {
	                String[] p = line.split(",");
	                if (p.length >= 7) {
	                    String username = p[0];
	                    String password = p[1];
	                    String ownerName = p[2];
	                    String gender = p[3];
	                    String accountType = p[4];
	                    double balance = Double.parseDouble(p[5]);
	                    boolean smsAlerts = Boolean.parseBoolean(p[6]);
	                    list.add(new Account(username, password, ownerName, gender, accountType, balance, smsAlerts));
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
	
	    public static void writeAccounts(List<Account> accounts) {
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
	            bw.write("username,password,ownerName,gender,accountType,balance,smsAlerts\n");
	            for (Account a : accounts) {
	                bw.write(a.getUsername() + "," + a.getPassword() + "," + a.getOwnerName() + "," +
	                        a.getGender() + "," + a.getAccountType() + "," + a.getBalance() + "," + a.isSmsAlerts() + "\n");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	    // =================== TRANSACTION REQUESTS ===================
	    public static List<TransactionRequest> readRequests() {
	        List<TransactionRequest> list = new ArrayList<>();
	        File file = new File(REQUESTS_FILE);
	        if (!file.exists()) return list;
	
	        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	            String line;
	            br.readLine(); // Skip header
	            while ((line = br.readLine()) != null) {
	                String[] p = line.split(",");
	                if (p.length >= 3) {
	                    String username = p[0];
	                    String type = p[1];
	                    double amount = Double.parseDouble(p[2]);
	                    list.add(new TransactionRequest(username, type, amount));
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
	
	    public static void writeRequests(List<TransactionRequest> requests) {
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REQUESTS_FILE))) {
	            bw.write("username,type,amount\n");
	            for (TransactionRequest r : requests) {
	                bw.write(r.getUsername() + "," + r.getType() + "," + r.getAmount() + "\n");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	    // =================== TRANSACTIONS ===================
	    public static List<Transaction> readTransactions() {
	        List<Transaction> list = new ArrayList<>();
	        File file = new File(TRANSACTIONS_FILE);
	        if (!file.exists()) return list;
	
	        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	            String line;
	            br.readLine(); // skip header
	            while ((line = br.readLine()) != null) {
	                String[] p = line.split(",");
	                if (p.length >= 4) {
	                    String username = p[0];
	                    String type = p[1];
	                    double amount = Double.parseDouble(p[2]);
	                    String when = p[3];
	                    list.add(new Transaction(username, type, amount, when));
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
	
	    public static void writeTransactions(List<Transaction> transactions) {
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE))) {
	            bw.write("username,type,amount,when\n");
	            for (Transaction t : transactions) {
	                bw.write(t.getUsername() + "," + t.getType() + "," + t.getAmount() + "," + t.getWhen() + "\n");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
