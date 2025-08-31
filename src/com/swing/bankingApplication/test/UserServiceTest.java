package com.swing.bankingApplication.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.swing.bankingApplication.UserService;

import java.util.List;

public class UserServiceTest {

    private UserService userService;

    @BeforeMethod
    public void setup() {
        userService = new UserService();
    }

    @Test
    public void testAuthenticate() {
        Assert.assertTrue(userService.authenticate("admin", "admin123"));
        Assert.assertFalse(userService.authenticate("admin", "wrongPass"));
    }

    @Test
    public void testCreateUserAndExists() {
        userService.createUser("newUser", "newPass", "New Name");
        Assert.assertTrue(userService.usernameExists("newUser"));
    }

    @Test
    public void testResetPassword() {
        userService.createUser("resetUser", "oldPass", "Reset Name");
        userService.resetPassword("resetUser", "newPass");
        Assert.assertTrue(userService.authenticate("resetUser", "newPass"));
    }

    @Test
    public void testGetAllUsernames() {
        List<String> usernames = userService.getAllUsernames();
        Assert.assertTrue(usernames.contains("admin"));
    }

    @Test
    public void testGetFullName() {
        Assert.assertEquals(userService.getFullName("admin"), "Bank Admin");
        Assert.assertEquals(userService.getFullName("unknown"), "unknown");
    }
}
