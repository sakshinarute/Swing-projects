package com.swing.bankingApplication.test;


import com.swing.bankingApplication.TransactionRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransactionRequestTest {

    @Test
    public void testRequestFieldsAndToString() {
        TransactionRequest r = new TransactionRequest("user1", "Withdraw", 1000.0);
        Assert.assertEquals(r.getUsername(), "user1");
        Assert.assertEquals(r.getType(), "Withdraw");
        Assert.assertEquals(r.getAmount(), 1000.0);
        Assert.assertTrue(r.toString().contains("user1"));
        Assert.assertTrue(r.toString().contains("Withdraw"));
    }
}

