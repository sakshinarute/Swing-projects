package com.swing.bankingApplication.test;

import com.swing.bankingApplication.UserService;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;

public class UserServiceTest {

    private UserService userService;

    @BeforeClass
    public void setup() {
        userService = new UserService();
    }

    @Test
    public void testAuthenticateValid() {
        Assert.assertTrue(userService.authenticate("admin", "admin123"));
    }

    @Test
    public void testAuthenticateInvalid() {
        Assert.assertFalse(userService.authenticate("admin", "wrongPass"));
    }

    @Test
    public void testUsernameExists() {
        Assert.assertTrue(userService.usernameExists("admin"));
        Assert.assertFalse(userService.usernameExists("unknown"));
    }

    @Test
    public void testCreateUserAndResetPassword() {
        userService.createUser("newUser", "pass123", "New User");
        Assert.assertTrue(userService.usernameExists("newUser"));
        userService.resetPassword("newUser", "newPass");
        Assert.assertTrue(userService.authenticate("newUser", "newPass"));
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
