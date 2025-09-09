package com.swing.bankingApplication.test;

import com.swing.bankingApplication.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;

public class AccountServiceTest {

    private AccountService service;

    @BeforeClass
    public void setup() {
        service = new AccountService();
    }

    @Test
    public void testAddAndFindAccount() {
        Account acc = new Account("testUser", "test123", "Test User", "Male", "Savings", 1000.0, true);
        service.addAccount(acc);
        Account found = service.findByUsername("testUser");
        Assert.assertNotNull(found);
        Assert.assertEquals(found.getOwnerName(), "Test User");
    }

    @Test
    public void testGetAllAccounts() {
        List<Account> accounts = service.getAllAccounts();
        Assert.assertTrue(accounts.size() > 0);
    }

    @Test
    public void testDeleteByUsername() {
        service.deleteByUsername("testUser");
        Assert.assertNull(service.findByUsername("testUser"));
    }

    @Test
    public void testDeposit() {
        boolean result = service.deposit("Smith", 500.0);
        Assert.assertTrue(result);
    }

    @Test
    public void testDepositInvalidAmount() {
        boolean result = service.deposit("Smith", -100.0);
        Assert.assertFalse(result);
    }

    @Test
    public void testWithdraw() {
        boolean result = service.withdraw("Smith", 100.0);
        Assert.assertTrue(result);
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        boolean result = service.withdraw("Smith", 100000.0);
        Assert.assertFalse(result);
    }

    @Test
    public void testAddAndRemoveRequest() {
        TransactionRequest request = new TransactionRequest("Smith", "Deposit", 500.0);
        service.addRequest(request);
        Assert.assertTrue(service.getRequests().contains(request));
        service.removeRequest(request);
        Assert.assertFalse(service.getRequests().contains(request));
    }

    @Test
    public void testGetTransactions() {
        List<Transaction> transactions = service.getTransactions();
        Assert.assertNotNull(transactions);
    }
}