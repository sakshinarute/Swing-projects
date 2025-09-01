package com.swing.bankingApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final Map<String, String> USERS = new HashMap<>();
    private static final Map<String, String> FULLNAME = new HashMap<>();

    public UserService() {
        if (USERS.isEmpty()) {
            USERS.put("admin", "admin123");
            USERS.put("Smith", "Smith123");
            USERS.put("Raju", "Raju123");

            FULLNAME.put("admin", "Bank Admin");
            FULLNAME.put("Smith", "Smith Georges");
            FULLNAME.put("Raju", "Rajesh Sharma");
        }
    }

    public boolean authenticate(String username, String password) {
        return USERS.containsKey(username) && USERS.get(username).equals(password);
    }

    public boolean usernameExists(String username) {
        return USERS.containsKey(username);
    }

    public void createUser(String username, String password, String fullName) {
        USERS.put(username, password);
        FULLNAME.put(username, fullName);
    }

    public void resetPassword(String username, String newPassword) {
        if (USERS.containsKey(username)) {
            USERS.put(username, newPassword);
        }
    }

    public List<String> getAllUsernames() {
        return new ArrayList<>(USERS.keySet());
    }

    public String getFullName(String username) {
        return FULLNAME.getOrDefault(username, username);
    }
}
