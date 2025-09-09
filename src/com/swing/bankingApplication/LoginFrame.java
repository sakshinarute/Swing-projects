package com.swing.bankingApplication;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public JTextField txtUser;
    public JPasswordField txtPass;
    public JComboBox<String> roleCombo;

    private AccountService accountService = new AccountService();

    public LoginFrame() {
        setTitle("Banking App - Login");
        setSize(480, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(false);

        // Gradient panel
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color c1 = new Color(235, 255, 245);
                Color c2 = new Color(245, 240, 255);
                g2.setPaint(new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        // Center form
        JPanel center = new JPanel(new GridLayout(3, 2, 8, 8));
        center.setOpaque(false);
        center.add(new JLabel("Username:"));
        txtUser = new JTextField();
        center.add(txtUser);

        center.add(new JLabel("Password:"));
        txtPass = new JPasswordField();
        center.add(txtPass);

        center.add(new JLabel("Role:"));
        roleCombo = new JComboBox<>(new String[]{"Admin", "User"});
        center.add(roleCombo);

        mainPanel.add(center, BorderLayout.CENTER);

        // South panel with buttons
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        south.setOpaque(false);

        JButton loginButton = createIconButton("login.png", 40, new Color(76, 175, 80), "Login");
        JButton exitButton = createIconButton("error.jpg", 40, new Color(244, 67, 54), "Exit");

        south.add(exitButton);
        south.add(loginButton);
        mainPanel.add(south, BorderLayout.SOUTH);

        // Actions
        loginButton.addActionListener(a -> doLogin());
        exitButton.addActionListener(a -> doExit());

        setVisible(true);
    }

    private JButton createIconButton(String iconName, int size, Color hoverColor, String tooltip) {
        JButton button = new JButton(loadIcon(iconName, size, size));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltip);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setOpaque(true);
                button.setBackground(hoverColor);
                button.setBorder(BorderFactory.createLineBorder(hoverColor, 5));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setOpaque(false);
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        });

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
                dispose();
                new AdminMenuPage();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials", "Error",
                        JOptionPane.ERROR_MESSAGE, loadIcon("error.jpg", 42, 42));
            }
        } else {
            // SwingWorker to avoid blocking UI
            new SwingWorker<Account, Void>() {
                @Override
                protected Account doInBackground() {
                    return accountService.findByUsername(u);
                }

                @Override
                protected void done() {
                    try {
                        Account acc = get();
                        if (acc != null && acc.getPassword().equals(p)) {
                            Session.loggedInUser = u;
                            Session.isAdmin = false;
                            dispose();
                            new UserDashboard();
                        } else {
                            JOptionPane.showMessageDialog(LoginFrame.this, "Invalid user credentials", "Error",
                                    JOptionPane.ERROR_MESSAGE, loadIcon("error.jpg", 42, 42));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }.execute();
        }
    }

    private void doExit() {
        int choice = JOptionPane.showConfirmDialog(this, "Do you really want to exit?", "Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, loadIcon("warning.jpg", 64, 64));
        if (choice == JOptionPane.YES_OPTION) System.exit(0);
    }
}