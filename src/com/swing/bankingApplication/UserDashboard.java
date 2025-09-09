package com.swing.bankingApplication;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class UserDashboard extends JFrame {
    private final AccountService accountService = new AccountService();

    public UserDashboard() {
        setTitle("User Dashboard - " + Session.loggedInUser);
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color c1 = new Color(224, 255, 255);
                Color c2 = new Color(240, 230, 250);
                g2.setPaint(new GradientPaint(0, 0, c1, 0, getHeight(), c2));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        // Top welcome
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setOpaque(false);
        JLabel welcome = new JLabel("Welcome, " + Session.loggedInUser);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 16));
        top.add(welcome);
        mainPanel.add(top, BorderLayout.NORTH);

        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        mainPanel.add(infoPanel, BorderLayout.CENTER);

        // Load account info in background
        new SwingWorker<Account, Void>() {
            @Override
            protected Account doInBackground() {
                return accountService.findByUsername(Session.loggedInUser);
            }

            @Override
            protected void done() {
                try {
                    Account acc = get();
                    if (acc != null) {
                        infoPanel.add(createInfoLabel("Owner Name: " + acc.getOwnerName()));
                        infoPanel.add(createInfoLabel("Gender: " + acc.getGender()));
                        infoPanel.add(createInfoLabel("Account Type: " + acc.getAccountType()));
                        infoPanel.add(createInfoLabel("Balance: " + acc.getBalance()));
                        infoPanel.add(createInfoLabel("SMS Alerts: " + (acc.isSmsAlerts() ? "Yes" : "No")));
                    } else {
                        infoPanel.add(createInfoLabel("No account found."));
                    }
                    infoPanel.revalidate();
                    infoPanel.repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();

        // Buttons panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        btnPanel.setOpaque(false);

        JButton depositBtn = createIconButton("deposit.jpg", "Request Deposit", new Color(60, 179, 113), 65);
        JButton withdrawBtn = createIconButton("withdrawal.png", "Request Withdraw", new Color(244, 67, 54), 60);
        JButton logoutBtn = createIconButton("logout.jpg", "Logout", new Color(70, 130, 180), 60);

        btnPanel.add(depositBtn);
        btnPanel.add(withdrawBtn);
        btnPanel.add(logoutBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        depositBtn.addActionListener(a -> requestTransaction("Deposit"));
        withdrawBtn.addActionListener(a -> requestTransaction("Withdraw"));
        logoutBtn.addActionListener(a -> doLogout());

        setVisible(true);
    }

    private JLabel createInfoLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setForeground(new Color(25, 25, 112));
        lbl.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JButton createIconButton(String iconName, String tooltip, Color hoverBorderColor, int size) {
        JButton btn = new JButton(loadIconSquare(iconName, size));
        btn.setToolTipText(tooltip);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(Color.BLACK, 2));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(false);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBorder(new LineBorder(hoverBorderColor.darker(), 8));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBorder(new LineBorder(Color.BLACK, 2));
            }
        });

        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                super.paint(g,c);
            }
        });

        return btn;
    }

    private ImageIcon loadIconSquare(String name, int size) {
        try {
            java.net.URL url = getClass().getResource("/com/swing/bankingApplication/icons/" + name);
            if (url == null) return null;
            ImageIcon ic = new ImageIcon(url);
            int ow = ic.getIconWidth();
            int oh = ic.getIconHeight();
            double scale = Math.min((double) size / ow, (double) size / oh);
            Image img = ic.getImage().getScaledInstance((int) (ow*scale), (int)(oh*scale), Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

    private void requestTransaction(String type) {
        new SwingWorker<Void, Void>() {
            double amt = 0;
            @Override
            protected Void doInBackground() {
                String s = JOptionPane.showInputDialog(UserDashboard.this, "Enter amount to " + type.toLowerCase() + ":");
                if (s == null) return null;
                try {
                    amt = Double.parseDouble(s);
                } catch (NumberFormatException e) { amt = -1; }
                return null;
            }

            @Override
            protected void done() {
                if (amt <= 0) {
                    JOptionPane.showMessageDialog(UserDashboard.this, "Invalid amount", "Error",
                            JOptionPane.ERROR_MESSAGE, loadIconSquare("error.jpg", 64));
                    return;
                }
                accountService.addRequest(new TransactionRequest(Session.loggedInUser, type, amt));
                JOptionPane.showMessageDialog(UserDashboard.this, type + " request submitted successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE, loadIconSquare("success.jpg", 64));
            }
        }.execute();
    }

    private void doLogout() {
        int choice = JOptionPane.showConfirmDialog(this, "Logout now?", "Logout",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, loadIconSquare("warning.jpg", 64));
        if (choice == JOptionPane.YES_OPTION) {
            Session.loggedInUser = null;
            Session.isAdmin = false;
            dispose();
            new LoginFrame();
        }
    }
}