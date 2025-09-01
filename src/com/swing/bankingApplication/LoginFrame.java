package com.swing.bankingApplication;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JComboBox<String> roleCombo;
    private AccountService accountService = new AccountService();

    public LoginFrame() {
        setTitle("Banking App - Login");
        setSize(420, 230);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout(8, 8));

        // Center panel for username, password, role
        JPanel center = new JPanel(new GridLayout(3, 2, 8, 8));
        center.setOpaque(false); 
        center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        center.add(new JLabel("Username:"));
        txtUser = new JTextField();
        center.add(txtUser);

        center.add(new JLabel("Password:"));
        txtPass = new JPasswordField();
        center.add(txtPass);

        center.add(new JLabel("Role:"));
        roleCombo = new JComboBox<>(new String[]{"Admin", "User"});
        center.add(roleCombo);

        add(center, BorderLayout.CENTER);

        // South panel for buttons
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.setOpaque(false); // transparent to show background
        JButton btnExit = new JButton("Exit", loadIcon("delete.jpg", 24, 24));
        JButton btnLogin = new JButton("Login", loadIcon("login.png", 24, 24));
        south.add(btnExit);
        south.add(btnLogin);
        add(south, BorderLayout.SOUTH);

        // Action listeners
        btnLogin.addActionListener(a -> doLogin());
        btnExit.addActionListener(a -> doExit());

        setVisible(true);
    }


    // ---------------- Icon Loader ----------------
    private ImageIcon loadIcon(String fileName, int width, int height) {
        java.net.URL url = getClass().getResource("icons/" + fileName);
        if (url == null) return null;
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // ---------------- Dialog Helper ----------------
    private void showDialog(String message, String iconFile, String title) {
        ImageIcon icon = loadIcon(iconFile, 64, 64);
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE, icon);
    }

    private int showConfirmDialog(String message, String iconFile, String title) {
        ImageIcon icon = loadIcon(iconFile, 64, 64);
        return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
    }

    // ---------------- Login Logic ----------------
    private void doLogin() {
        String u = txtUser.getText().trim();
        String p = new String(txtPass.getPassword()).trim();
        String role = (String) roleCombo.getSelectedItem();

        if (u.isEmpty() || p.isEmpty()) {
            showDialog("Enter username and password", "warning.jpg", "Validation");
            return;
        }

        if ("Admin".equals(role)) {
            if ("admin".equals(u) && "admin123".equals(p)) {
                Session.loggedInUser = "admin";
                Session.isAdmin = true;
                dispose();
                new AdminDashboard();
            } else {
                showDialog("Invalid admin credentials", "error.jpg", "Error");
            }
        } else {
            Account acc = accountService.findByUsername(u);
            if (acc != null && acc.getPassword().equals(p)) {
                Session.loggedInUser = u;
                Session.isAdmin = false;
                dispose();
                new UserDashboard();
            } else {
                showDialog("Invalid user credentials", "error.jpg", "Error");
            }
        }
    }

    // ---------------- Exit Logic ----------------
    private void doExit() {
        int choice = showConfirmDialog("Do you really want to exit?", "warning.jpg", "Exit");
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}