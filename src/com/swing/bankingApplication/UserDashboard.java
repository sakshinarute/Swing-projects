package com.swing.bankingApplication;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {
    private AccountService accountService = new AccountService();

    public UserDashboard() {
        setTitle("User Dashboard - " + Session.loggedInUser);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        // ---------------- Menu ----------------
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem logout = new JMenuItem("Logout");
        file.add(logout);
        mb.add(file);
        setJMenuBar(mb);

        logout.addActionListener(a -> doLogout());

        getContentPane().setLayout(new BorderLayout());

        // ---------------- Top Panel ----------------
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setOpaque(false);
        top.add(new JLabel("Welcome, " + Session.loggedInUser));
        getContentPane().add(top, BorderLayout.NORTH);

        // ---------------- User Info ----------------
        Account acc = accountService.findByUsername(Session.loggedInUser);
        if (acc != null) {
            JTextArea area = new JTextArea();
            area.setEditable(false);
            StringBuilder sb = new StringBuilder();
            sb.append("Owner Name: ").append(acc.getOwnerName()).append("\n");
            sb.append("Gender: ").append(acc.getGender()).append("\n");
            sb.append("Account Type: ").append(acc.getAccountType()).append("\n");
            sb.append("Balance: ").append(acc.getBalance()).append("\n");
            sb.append("SMS Alerts: ").append(acc.isSmsAlerts() ? "Yes" : "No").append("\n");
            area.setText(sb.toString());
            getContentPane().add(new JScrollPane(area), BorderLayout.CENTER);
        } else {
            getContentPane().add(new JLabel("No account found for your username."), BorderLayout.CENTER);
        }

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
    private int showConfirmDialog(String message, String iconFile, String title) {
        ImageIcon icon = loadIcon(iconFile, 64, 64);
        return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
    }

    private void showDialog(String message, String iconFile, String title) {
        ImageIcon icon = loadIcon(iconFile, 64, 64);
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE, icon);
    }

    // ---------------- Logout ----------------
    private void doLogout() {
        int choice = showConfirmDialog("Logout now?", "warning.jpg", "Logout");
        if (choice == JOptionPane.YES_OPTION) {
            Session.loggedInUser = null;
            Session.isAdmin = false;
            dispose();
            new LoginFrame();
        }
    }
}
