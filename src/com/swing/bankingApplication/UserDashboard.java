package com.swing.bankingApplication;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {
    private final AccountService accountService = new AccountService();

    public UserDashboard() {
        setTitle("User Dashboard - " + Session.loggedInUser);
        setSize(600, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //(Welcome Message)
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Welcome, " + Session.loggedInUser));
        add(top, BorderLayout.NORTH);

        // Center: Account Details + Actions
        Account acc = accountService.findByUsername(Session.loggedInUser);
        JPanel center = new JPanel(new GridLayout(6, 2, 8, 8));
        center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (acc != null) {
            center.add(new JLabel("Owner Name:")); center.add(new JLabel(acc.getOwnerName()));
            center.add(new JLabel("Gender:")); center.add(new JLabel(acc.getGender()));
            center.add(new JLabel("Account Type:")); center.add(new JLabel(acc.getAccountType()));
            JLabel balLabel = new JLabel(String.valueOf(acc.getBalance()));
            center.add(new JLabel("Balance:")); center.add(balLabel);
            center.add(new JLabel("SMS Alerts:")); center.add(new JLabel(acc.isSmsAlerts() ? "Yes" : "No"));

            //Deposit and Withdraw Buttons
            JButton btnDeposit = new JButton("Request Deposit", loadIcon("add.png", 24, 24));
            JButton btnWithdraw = new JButton("Request Withdraw", loadIcon("delete.jpg", 24, 24));
            center.add(btnDeposit); center.add(btnWithdraw);

            //Deposit Action
            btnDeposit.addActionListener(a -> {
                String s = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
                if (s == null) return;
                try {
                    double amt = Double.parseDouble(s);
                    if (amt <= 0) {
                        JOptionPane.showMessageDialog(this, "Amount must be > 0", "Error",
                                JOptionPane.ERROR_MESSAGE, loadIcon("error.jpg", 64, 64));
                        return;
                    }
                    accountService.addRequest(new TransactionRequest(acc.getUsername(), "Deposit", amt));
                    JOptionPane.showMessageDialog(this, "Deposit request submitted for admin approval",
                            "Submitted", JOptionPane.INFORMATION_MESSAGE, loadIcon("info.jpg", 64, 64));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount", "Error",
                            JOptionPane.ERROR_MESSAGE, loadIcon("error.jpg", 64, 64));
                }
            });

            //Withdraw Action
            btnWithdraw.addActionListener(a -> {
                String s = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
                if (s == null) return;
                try {
                    double amt = Double.parseDouble(s);
                    if (amt <= 0) {
                        JOptionPane.showMessageDialog(this, "Amount must be > 0", "Error",
                                JOptionPane.ERROR_MESSAGE, loadIcon("error.jpg", 64, 64));
                        return;
                    }
                    accountService.addRequest(new TransactionRequest(acc.getUsername(), "Withdraw", amt));
                    JOptionPane.showMessageDialog(this, "Withdraw request submitted for admin approval",
                            "Submitted", JOptionPane.INFORMATION_MESSAGE, loadIcon("info.jpg", 64, 64));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount", "Error",
                            JOptionPane.ERROR_MESSAGE, loadIcon("error.jpg", 64, 64));
                }
            });
        } else {
            center.add(new JLabel("No account found for your username."));
            center.add(new JLabel());
        }

        add(center, BorderLayout.CENTER);

        //Bottom: Logout Button
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutBtn = new JButton("Logout", loadIcon("logout.jpg", 24, 24));
        bottom.add(logoutBtn);
        add(bottom, BorderLayout.SOUTH);

        // Logout Action
        logoutBtn.addActionListener(a -> doLogout());

        setVisible(true);
    }

    private ImageIcon loadIcon(String name, int w, int h) {
        try {
            java.net.URL url = getClass().getResource("/com/swing/bankingApplication/icons/" + name);
            if (url == null) {
                System.err.println("Icon not found: " + name);
                return null;
            }
            ImageIcon ic = new ImageIcon(url);
            Image img = ic.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

    private void doLogout() {
        int choice = JOptionPane.showConfirmDialog(this, "Logout now?", "Logout",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, loadIcon("warning.jpg", 64, 64));
        if (choice == JOptionPane.YES_OPTION) {
            Session.loggedInUser = null;
            Session.isAdmin = false;
            dispose();
            new LoginFrame();
        }
    }
}
