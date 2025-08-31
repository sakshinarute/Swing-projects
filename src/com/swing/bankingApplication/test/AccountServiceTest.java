package com.swing.bankingApplication.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.swing.bankingApplication.Account;
import com.swing.bankingApplication.AccountService;

import java.util.List;

public class AccountServiceTest {

    private AccountService service;

    @BeforeMethod
    public void setup() {
        service = new AccountService();
    }

    @Test
    public void testAddAccountAndFind() {
        Account acc = new Account("u1", "p1", "User One", "Male", "Savings", 1000, true);
        service.addAccount(acc);

        Account found = service.findByUsername("u1");
        Assert.assertNotNull(found);
        Assert.assertEquals(found.getOwnerName(), "User One");
    }

    @Test
    public void testDeleteAccount() {
        Account acc = new Account("u2", "p2", "User Two", "Female", "Current", 2000, false);
        service.addAccount(acc);
        service.deleteByUsername("u2");

        Account found = service.findByUsername("u2");
        Assert.assertNull(found);
    }

    @Test
    public void testGetAllAccounts() {
        List<Account> accounts = service.getAllAccounts();
        Assert.assertNotNull(accounts);
        Assert.assertTrue(accounts.size() >= 2); // Because of static initial accounts
    }
}

