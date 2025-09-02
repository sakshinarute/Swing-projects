package com.swing.bankingApplication;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public JTextField txtUser;
    public JPasswordField txtPass;
    public JComboBox<String> roleCombo;
    public JButton loginButton;
    public JButton btnExit;

    private AccountService accountService = new AccountService();

    public LoginFrame() {
        setTitle("Banking App - Login");
        setSize(480, 270);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8,8));

        // center form
        JPanel center = new JPanel(new GridLayout(3,2,8,8));
        center.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
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

        // south buttons
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExit = new JButton("Exit", loadIcon("delete.jpg", 20, 20));
        loginButton = new JButton("Login", loadIcon("login.png", 20, 20));
        south.add(btnExit);
        south.add(loginButton);
        add(south, BorderLayout.SOUTH);

        // actions
        loginButton.addActionListener(a -> doLogin());
        btnExit.addActionListener(a -> doExit());

        setVisible(true);
    }

    // load icon from package path and resize
    private ImageIcon loadIcon(String name, int w, int h) {
        try {
            java.net.URL url = getClass().getResource("/com/swing/bankingApplication/icons/" + name);
            if (url == null) return null;
            ImageIcon ic = new ImageIcon(url);
            Image img = ic.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

    private void doLogin() {
        String u = txtUser.getText().trim();
        String p = new String(txtPass.getPassword()).trim();
        String role = (String) roleCombo.getSelectedItem();

        if (u.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password", "Validation",
                    JOptionPane.INFORMATION_MESSAGE, loadIcon("warning.jpg", 64, 64));
            return;
        }

        if ("Admin".equals(role)) {
            if ("admin".equals(u) && "admin123".equals(p)) {
                Session.loggedInUser = "admin";
                Session.isAdmin = true;
                JOptionPane.showMessageDialog(this, "Logged in as admin", "Success",
                        JOptionPane.INFORMATION_MESSAGE, loadIcon("success.jpg", 64, 64));
                dispose();
                new AdminDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials", "Error",
                        JOptionPane.ERROR_MESSAGE, loadIcon("error.jpg", 64, 64));
            }
        } else {
            Account acc = accountService.findByUsername(u);
            if (acc != null && acc.getPassword().equals(p)) {
                Session.loggedInUser = u;
                Session.isAdmin = false;
                JOptionPane.showMessageDialog(this, "Welcome " + acc.getOwnerName(), "Success",
                        JOptionPane.INFORMATION_MESSAGE, loadIcon("success.jpg", 64, 64));
                dispose();
                new UserDashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid user credentials", "Error",
                        JOptionPane.ERROR_MESSAGE, loadIcon("error.jpg", 64, 64));
            }
        }
    }

    private void doExit() {
        int choice = JOptionPane.showConfirmDialog(this, "Do you really want to exit?", "Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, loadIcon("warning.jpg", 64, 64));
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
