package com.swing.bankingApplication.test;

import com.swing.bankingApplication.Account;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountTest {

    @Test
    public void testAccountFields() {
        Account account = new Account("user1", "pass", "John Doe", "Male", "Savings", 1000.0, true);
        Assert.assertEquals(account.getUsername(), "user1");
        Assert.assertEquals(account.getPassword(), "pass");
        Assert.assertEquals(account.getOwnerName(), "John Doe");
        Assert.assertEquals(account.getGender(), "Male");
        Assert.assertEquals(account.getAccountType(), "Savings");
        Assert.assertEquals(account.getBalance(), 1000.0);
        Assert.assertTrue(account.isSmsAlerts());
    }

    @Test
    public void testSetters() {
        Account account = new Account("user2", "pass", "Alice", "Female", "Current", 500.0, false);
        account.setPassword("newPass");
        account.setOwnerName("Alice Smith");
        account.setGender("Other");
        account.setAccountType("Savings");
        account.setBalance(2000.0);
        account.setSmsAlerts(true);

        Assert.assertEquals(account.getPassword(), "newPass");
        Assert.assertEquals(account.getOwnerName(), "Alice Smith");
        Assert.assertEquals(account.getGender(), "Other");
        Assert.assertEquals(account.getAccountType(), "Savings");
        Assert.assertEquals(account.getBalance(), 2000.0);
        Assert.assertTrue(account.isSmsAlerts());
    }

    @Test
    public void testToString() {
        Account account = new Account("user3", "pass", "John", "Male", "Savings", 1500.0, true);
        String str = account.toString();
        Assert.assertTrue(str.contains("user3"));
        Assert.assertTrue(str.contains("1500.0"));
    }
}