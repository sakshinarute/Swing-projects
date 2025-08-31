package com.swing.bankingApplication.test;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.swing.bankingApplication.Account;

public class AccountTest {

    @Test
    public void testAccountCreationAndGetters() {
        Account acc = new Account("user1", "pass1", "John Doe", "Male", "Savings", 1000.0, true);

        Assert.assertEquals(acc.getUsername(), "user1");
        Assert.assertEquals(acc.getPassword(), "pass1");
        Assert.assertEquals(acc.getOwnerName(), "John Doe");
        Assert.assertEquals(acc.getGender(), "Male");
        Assert.assertEquals(acc.getAccountType(), "Savings");
        Assert.assertEquals(acc.getBalance(), 1000.0);
        Assert.assertTrue(acc.isSmsAlerts());
    }

    @Test
    public void testSetters() {
        Account acc = new Account("u", "p", "name", "Female", "Current", 500, false);

        acc.setPassword("newPass");
        acc.setOwnerName("Jane");
        acc.setGender("Other");
        acc.setAccountType("Savings");
        acc.setBalance(2000);
        acc.setSmsAlerts(true);

        Assert.assertEquals(acc.getPassword(), "newPass");
        Assert.assertEquals(acc.getOwnerName(), "Jane");
        Assert.assertEquals(acc.getGender(), "Other");
        Assert.assertEquals(acc.getAccountType(), "Savings");
        Assert.assertEquals(acc.getBalance(), 2000.0);
        Assert.assertTrue(acc.isSmsAlerts());
    }
}
