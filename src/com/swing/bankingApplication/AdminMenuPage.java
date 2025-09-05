package com.swing.bankingApplication;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminMenuPage extends JFrame {
    private final AccountService accountService = new AccountService();

    public AdminMenuPage() {
        setTitle("Admin Menu - " + Session.loggedInUser);
        setSize(500, 450); // enough height
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Gradient panel
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color c1 = new Color(235, 255, 245); // very light mint
                Color c2 = new Color(245, 240, 255); // very light lavender
                g2.setPaint(new GradientPaint(0, 0, c1, 0, getHeight(), c2));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Welcome label
        JLabel lblWelcome = new JLabel("Welcome, " + Session.loggedInUser);
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        lblWelcome.setForeground(new Color(50, 50, 50));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(lblWelcome);

        // Buttons
        JButton btnManage = createSimpleButton("accountManagement.png", 64, new Color(70, 130, 180), "Manage Accounts");
        JButton btnTransactions = createSimpleButton("transaction.jpg", 64, new Color(60, 179, 113), "View Transactions");
        JButton btnLogout = createSimpleButton("logout.jpg", 64, new Color(244, 67, 54), "Logout");

        mainPanel.add(btnManage);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(btnTransactions);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(btnLogout);

        add(mainPanel);

        // Actions
        btnManage.addActionListener(e -> {
            dispose();
            new AdminDashboard();
        });
        btnTransactions.addActionListener(e -> showTransactions());
        btnLogout.addActionListener(e -> {
            Session.loggedInUser = null;
            Session.isAdmin = false;
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    // Simple button with hover color
    private JButton createSimpleButton(String iconName, int size, Color hoverColor, String tooltip) {
        JButton button = new JButton(loadIcon(iconName, size, size));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // default thin black border
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltip);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Hover effect: background + thicker border
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setOpaque(true);
                button.setBackground(hoverColor);
                button.setBorder(BorderFactory.createLineBorder(hoverColor, 5)); // thicker border on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setOpaque(false);
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // revert to thin black border
            }
        });

        // Rounded background
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (button.isOpaque()) {
                    g2.setColor(button.getBackground());
                    g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                }
                super.paint(g2, c);
                g2.dispose();
            }
        });

        return button;
    }


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

    private void showTransactions() {
        List<Transaction> list = accountService.getTransactions();
        StringBuilder sb = new StringBuilder();
        for (Transaction t : list) {
            sb.append(t.getUsername()).append(" - ").append(t.getType())
              .append(" - ").append(t.getAmount())
              .append(" - ").append(t.getWhen()).append("\n");
        }
        JTextArea ta = new JTextArea(sb.toString());
        ta.setEditable(false);
        JScrollPane scroll = new JScrollPane(ta);
        JFrame transFrame = new JFrame("Transaction History");
        transFrame.setSize(500, 400);
        transFrame.add(scroll);
        transFrame.setLocationRelativeTo(null);
        transFrame.setVisible(true);
    }
}
