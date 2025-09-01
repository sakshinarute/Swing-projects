package com.swing.bankingApplication.test;

import com.swing.bankingApplication.Session;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SessionTest {

    @Test
    public void testSessionSetAndReset() {
        Session.loggedInUser = "testUser";
        Session.isAdmin = true;
        Assert.assertEquals(Session.loggedInUser, "testUser");
        Assert.assertTrue(Session.isAdmin);

        Session.loggedInUser = null;
        Session.isAdmin = false;
        Assert.assertNull(Session.loggedInUser);
        Assert.assertFalse(Session.isAdmin);
    }
}

