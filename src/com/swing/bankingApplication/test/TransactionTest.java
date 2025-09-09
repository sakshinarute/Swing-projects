package com.swing.bankingApplication.test;

import com.swing.bankingApplication.Transaction;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransactionTest {

    @Test
    public void testTransactionFields() {
    	Transaction t = new Transaction("user1", "Deposit", 500.0, "2025-09-09 12:00:00");
    	Assert.assertEquals(t.getUsername(), "user1");
    	Assert.assertEquals(t.getType(), "Deposit");
    	Assert.assertEquals(t.getAmount(), 500.0);
    	Assert.assertEquals(t.getWhen(), "2025-09-09 12:00:00");
    }
}

