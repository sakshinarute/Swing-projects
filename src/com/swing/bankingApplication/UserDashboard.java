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

        // Gradient background panel
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color c1 = new Color(224, 255, 255); // light cyan
                Color c2 = new Color(240, 230, 250); // lavender
                GradientPaint gp = new GradientPaint(0, 0, c1, 0, getHeight(), c2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        // Top welcome label
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setOpaque(false);
        JLabel welcome = new JLabel("Welcome, " + Session.loggedInUser);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 16));
        top.add(welcome);
        mainPanel.add(top, BorderLayout.NORTH);

        // Info labels panel (left-aligned)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        Account acc = accountService.findByUsername(Session.loggedInUser);
        if (acc != null) {
            infoPanel.add(createInfoLabel("Owner Name: " + acc.getOwnerName()));
            infoPanel.add(createInfoLabel("Gender: " + acc.getGender()));
            infoPanel.add(createInfoLabel("Account Type: " + acc.getAccountType()));
            infoPanel.add(createInfoLabel("Balance: " + acc.getBalance()));
            infoPanel.add(createInfoLabel("SMS Alerts: " + (acc.isSmsAlerts() ? "Yes" : "No")));
        } else {
            infoPanel.add(createInfoLabel("No account found."));
        }
        mainPanel.add(infoPanel, BorderLayout.CENTER);

        // Buttons panel at bottom (centered)
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        btnPanel.setOpaque(false);

        // Button dimensions
        int depositSize = 65;   // slightly bigger
        int otherSize = 60;     // smaller for withdraw & logout

        JButton depositBtn = createIconButton("deposit.jpg", "Request Deposit", new Color(60, 179, 113), depositSize);
        JButton withdrawBtn = createIconButton("withdrawal.png", "Request Withdraw", new Color(244, 67, 54), otherSize);
        JButton logoutBtn = createIconButton("logout.jpg", "Logout", new Color(70, 130, 180), otherSize);

        btnPanel.add(depositBtn);
        btnPanel.add(withdrawBtn);
        btnPanel.add(logoutBtn);

        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        // Button actions
        if (acc != null) {
            depositBtn.addActionListener(a -> requestTransaction(acc, "Deposit"));
            withdrawBtn.addActionListener(a -> requestTransaction(acc, "Withdraw"));
            logoutBtn.addActionListener(a -> doLogout());
        }

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

    // Button creation: only border changes on hover
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
                btn.setBorder(new LineBorder(hoverBorderColor.darker(), 8)); // thicker border on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBorder(new LineBorder(Color.BLACK, 2));
            }
        });

        // Rounded corners
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paint(g2, c);
                g2.dispose();
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
            int newW = (int) (ow * scale);
            int newH = (int) (oh * scale);

            Image img = ic.getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

    private void requestTransaction(Account acc, String type) {
        String s = JOptionPane.showInputDialog(this, "Enter amount to " + type.toLowerCase() + ":");
        if (s == null) return;
        try {
            double amt = Double.parseDouble(s);
            if (amt <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be > 0", "Error",
                        JOptionPane.ERROR_MESSAGE, loadIconSquare("error.jpg", 64));
                return;
            }
            accountService.addRequest(new TransactionRequest(acc.getUsername(), type, amt));
            JOptionPane.showMessageDialog(this, type + " request submitted for admin approval",
                    "Submitted", JOptionPane.INFORMATION_MESSAGE, loadIconSquare("info.jpg", 64));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount", "Error",
                    JOptionPane.ERROR_MESSAGE, loadIconSquare("error.jpg", 64));
        }
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
