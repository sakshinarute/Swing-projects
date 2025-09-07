package com.swing.bankingApplication;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {

    private final AccountService accountService = new AccountService();
    public JTable table;
    public DefaultTableModel model;
    private JTree navTree;

    private JTable requestTable;
    private DefaultTableModel requestModel;

    public AdminDashboard() {
        setTitle("Admin Dashboard - " + Session.loggedInUser);
        setSize(1000, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Plain main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        // Menu bar
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(a -> doLogout());
        file.add(logout);
        mb.add(file);
        setJMenuBar(mb);

        // Top toolbar buttons
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);
        tb.setOpaque(false);

        JButton btnAdd = createToolbarButton("add.png", "Add Account", new Color(70, 130, 180), 40, 40);
        btnAdd.addActionListener(a -> showAddDialog());

        JButton btnDelete = createToolbarButton("delete.jpg", "Delete Selected", new Color(244, 67, 54), 40, 40);
        btnDelete.addActionListener(a -> deleteSelected());

        JButton btnRefresh = createToolbarButton("refresh.png", "Refresh", new Color(255, 193, 7), 40, 40);
        btnRefresh.addActionListener(a -> refreshTable());

        JButton btnSave = createToolbarButton("save.jpg", "Save", new Color(60, 179, 113), 40, 40);
        btnSave.addActionListener(a -> saveChanges());

        tb.add(btnAdd); tb.add(Box.createHorizontalStrut(10));
        tb.add(btnDelete); tb.add(Box.createHorizontalStrut(10));
        tb.add(btnRefresh); tb.add(Box.createHorizontalStrut(10));
        tb.add(btnSave);

        mainPanel.add(tb, BorderLayout.NORTH);

        // Navigation tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Navigation");
        DefaultMutableTreeNode accNode = new DefaultMutableTreeNode("Accounts");
        root.add(accNode);
        accNode.add(new DefaultMutableTreeNode("Savings"));
        accNode.add(new DefaultMutableTreeNode("Current"));
        navTree = new JTree(root);
        navTree.setRootVisible(false);
        navTree.setBorder(new EmptyBorder(5,5,5,5));
        navTree.setBackground(Color.WHITE);
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) navTree.getCellRenderer();
        renderer.setBackgroundNonSelectionColor(Color.WHITE);
        renderer.setBackgroundSelectionColor(new Color(173, 216, 230));
        renderer.setTextNonSelectionColor(Color.BLACK);
        renderer.setTextSelectionColor(Color.BLACK);

        navTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode sel = (DefaultMutableTreeNode) navTree.getLastSelectedPathComponent();
            if (sel == null) return;
            String val = sel.toString();
            if ("Savings".equals(val) || "Current".equals(val)) filterByType(val);
            else refreshTable();
        });

        // Tables
        String[] cols = {"Username", "Name", "Gender", "Type", "Balance", "SMS Alerts"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return col != 0; }
            @Override public Class<?> getColumnClass(int col) {
                if (col == 4) return Double.class;
                if (col == 5) return Boolean.class;
                return String.class;
            }
        };
        table = new JTable(model);
        styleTable(table);

        String[] reqCols = {"Username", "Type", "Amount"};
        requestModel = new DefaultTableModel(reqCols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
            @Override public Class<?> getColumnClass(int col) {
                if (col == 2) return Double.class;
                return String.class;
            }
        };
        requestTable = new JTable(requestModel);
        styleTable(requestTable);

        JScrollPane acctScroll = new JScrollPane(table);
        JScrollPane reqScroll = new JScrollPane(requestTable);
        JSplitPane vert = new JSplitPane(JSplitPane.VERTICAL_SPLIT, acctScroll, reqScroll);
        vert.setDividerLocation(360);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(navTree), vert);
        split.setDividerLocation(220);
        mainPanel.add(split, BorderLayout.CENTER);

        // Bottom buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        bottom.setOpaque(false);

        JButton btnShowUsers = createToolbarButton("info.jpg", "Show Usernames", new Color(255, 215, 0), 40, 40);
        btnShowUsers.addActionListener(a -> showUsernames());

        JButton btnApprove = createToolbarButton("success.jpg", "Approve Request", new Color(60, 179, 113), 40, 40);
        btnApprove.addActionListener(a -> approveSelectedRequest());

        JButton btnReject = createToolbarButton("error.jpg", "Reject Request", new Color(244, 67, 54), 40, 40);
        btnReject.addActionListener(a -> rejectSelectedRequest());

        bottom.add(btnShowUsers);
        bottom.add(btnApprove);
        bottom.add(btnReject);

        mainPanel.add(bottom, BorderLayout.SOUTH);

        // Load initial data
        refreshTable();
        loadRequestData();

        setVisible(true);
    }

    // =================== Button Creation Helper ===================
    private JButton createToolbarButton(String iconName, String tooltip, Color hoverColor, int w, int h) {
        JButton btn = new JButton(loadIcon(iconName, w, h));
        btn.setToolTipText(tooltip);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setOpaque(false);
                btn.setBorder(BorderFactory.createLineBorder(hoverColor, 4));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setOpaque(false);
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        });

        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (btn.isOpaque()) {
                    g2.setColor(btn.getBackground());
                    g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                }
                super.paint(g2, c);
                g2.dispose();
            }
        });

        return btn;
    }

    private ImageIcon loadIcon(String name, int w, int h) {
        try {
            java.net.URL url = getClass().getResource("/com/swing/bankingApplication/icons/" + name);
            if (url == null) return null;
            ImageIcon ic = new ImageIcon(url);
            Image img = ic.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) { return null; }
    }

    private void styleTable(JTable table) {
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.setSelectionBackground(new Color(100,149,237));
        table.setSelectionForeground(Color.WHITE);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(220,220,220));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(200,200,200));
        table.getTableHeader().setForeground(Color.DARK_GRAY);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                if (isSelected) {
                    c.setBackground(new Color(100,149,237));
                    c.setForeground(Color.WHITE);
                } else {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(240,248,255));
                        c.setForeground(new Color(25,25,112));
                    } else {
                        c.setBackground(new Color(224,255,255));
                        c.setForeground(new Color(0,100,0));
                    }
                }
                setBorder(new EmptyBorder(0,5,0,5));
                return c;
            }
        });
    }

    // =================== Dialog Helpers ===================
    private void showDialog(String message, String iconName, String title) {
        ImageIcon icon = loadIcon(iconName, 64, 64);
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE, icon);
    }

    private int showConfirm(String message, String iconName, String title) {
        ImageIcon icon = loadIcon(iconName, 64, 64);
        return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
    }

    // =================== Functional Methods ===================
 // Threaded refreshTable
    public void refreshTable() {
        new SwingWorker<List<Account>, Void>() {
            @Override
            protected List<Account> doInBackground() {
                return accountService.getAllAccounts(); // fetch in background
            }

            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> {
                    try {
                        List<Account> all = get();
                        model.setRowCount(0);
                        for (Account a : all) {
                            model.addRow(new Object[]{a.getUsername(), a.getOwnerName(), a.getGender(),
                                    a.getAccountType(), a.getBalance(), a.isSmsAlerts()});
                        }
                    } catch (Exception e) { e.printStackTrace(); }
                });
            }
        }.execute();
    }

    private void filterByType(String type) {
        SwingUtilities.invokeLater(() -> {
            model.setRowCount(0);
            List<Account> all = accountService.getAllAccounts();
            for (Account a : all) {
                if (a.getAccountType().equalsIgnoreCase(type)) {
                    model.addRow(new Object[]{a.getUsername(), a.getOwnerName(), a.getGender(), a.getAccountType(), a.getBalance(), a.isSmsAlerts()});
                }
            }
        });
    }

    private void deleteSelected() {
        int r = table.getSelectedRow();
        if (r < 0) { showDialog("Select a row to delete", "info.jpg", "Info"); return; }
        String uname = model.getValueAt(r,0).toString();
        int conf = showConfirm("Delete account for '" + uname + "'?", "warning.jpg", "Confirm Delete");
        if (conf == JOptionPane.YES_OPTION) {
            accountService.deleteByUsername(uname);
            refreshTable();
            showDialog("Deleted account for " + uname, "success.jpg", "Success");
        }
    }

    private void saveChanges() {
        for (int i=0;i<model.getRowCount();i++) {
            String username = (String) model.getValueAt(i,0);
            Account acc = accountService.findByUsername(username);
            if (acc != null) {
                acc.setOwnerName((String) model.getValueAt(i,1));
                acc.setGender((String) model.getValueAt(i,2));
                acc.setAccountType((String) model.getValueAt(i,3));
                Object balObj = model.getValueAt(i,4);
                double bal = balObj instanceof Double ? (Double) balObj : Double.parseDouble(balObj.toString());
                acc.setBalance(bal);
                Object smsObj = model.getValueAt(i,5);
                acc.setSmsAlerts(smsObj instanceof Boolean ? (Boolean) smsObj : Boolean.parseBoolean(smsObj.toString()));
            }
        }
        showDialog("Changes saved successfully!", "success.jpg", "Success");
    }

    private void showUsernames() {
        StringBuilder sb = new StringBuilder();
        for (Account a : accountService.getAllAccounts()) sb.append(a.getUsername()).append("\n");
        JTextArea ta = new JTextArea(sb.toString());
        ta.setEditable(false);
        JOptionPane.showMessageDialog(this, ta, "Usernames", JOptionPane.INFORMATION_MESSAGE, loadIcon("info.jpg",64,64));
    }

    private void doLogout() {
        int ch = showConfirm("Logout now?", "warning.jpg", "Logout");
        if (ch == JOptionPane.YES_OPTION) {
            Session.loggedInUser = null;
            Session.isAdmin = false;
            dispose();
            new LoginFrame();
        }
    }

    private void showAddDialog() {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        JPanel r1 = new JPanel(new GridLayout(2,2,6,6));
        JTextField txtUser = new JTextField();
        JPasswordField txtPwd = new JPasswordField();
        r1.add(new JLabel("Username:")); r1.add(txtUser);
        r1.add(new JLabel("Password:")); r1.add(txtPwd);

        JPanel r2 = new JPanel(new GridLayout(2,2,6,6));
        JTextField txtName = new JTextField();
        JRadioButton rMale = new JRadioButton("Male");
        JRadioButton rFemale = new JRadioButton("Female");
        JRadioButton rOther = new JRadioButton("Other");
        ButtonGroup bg = new ButtonGroup(); bg.add(rMale); bg.add(rFemale); bg.add(rOther);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); genderPanel.add(rMale); genderPanel.add(rFemale); genderPanel.add(rOther);
        r2.add(new JLabel("Owner Name:")); r2.add(txtName);
        r2.add(new JLabel("Gender:")); r2.add(genderPanel);

        JPanel r3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        r3.add(new JLabel("Account Type:"));
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Savings","Current"});
        r3.add(typeBox);

        JPanel r4 = new JPanel(new GridLayout(1,2,6,6));
        JTextField txtBal = new JTextField();
        r4.add(new JLabel("Initial Balance:")); r4.add(txtBal);

        JPanel r5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox chkSms = new JCheckBox("Subscribe to SMS Alerts?");
        r5.add(chkSms);

        form.add(r1); form.add(r2); form.add(r3); form.add(r4); form.add(r5);

        int res = JOptionPane.showConfirmDialog(this, form, "Add Account", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, loadIcon("info.jpg",64,64));
        if (res == JOptionPane.OK_OPTION) {
            String uname = txtUser.getText().trim();
            String pwd = new String(txtPwd.getPassword()).trim();
            String name = txtName.getText().trim();
            String gender = rMale.isSelected() ? "Male" : rFemale.isSelected() ? "Female" : rOther.isSelected() ? "Other" : "Unspecified";
            String type = (String) typeBox.getSelectedItem();
            double bal = 0;
            try { bal = Double.parseDouble(txtBal.getText().trim()); } catch (Exception ex) {}
            boolean sms = chkSms.isSelected();

            if (uname.isEmpty() || pwd.isEmpty() || name.isEmpty()) {
                showDialog("Username, password and name are required", "error.jpg", "Error");
                return;
            }
            if (accountService.findByUsername(uname) != null) {
                showDialog("Username already exists", "error.jpg", "Error");
                return;
            }

            accountService.addAccount(new Account(uname, pwd, name, gender, type, bal, sms));
            showDialog("Account created for user: " + uname, "success.jpg", "Success");
            refreshTable();
            loadRequestData();
        }
    }

    // Threaded loadRequestData
    private void loadRequestData() {
        new SwingWorker<List<TransactionRequest>, Void>() {
            @Override
            protected List<TransactionRequest> doInBackground() {
                return accountService.getRequests(); // fetch requests in background
            }

            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> {
                    try {
                        List<TransactionRequest> requests = get();
                        requestModel.setRowCount(0);
                        for (TransactionRequest r : requests) {
                            requestModel.addRow(new Object[]{r.getUsername(), r.getType(), r.getAmount()});
                        }
                    } catch (Exception e) { e.printStackTrace(); }
                });
            }
        }.execute();
    }
    // =================== Threaded Approve/Reject ===================
    private void approveSelectedRequest() {
        int r = requestTable.getSelectedRow();
        if (r < 0) { showDialog("Select a request to approve", "info.jpg", "Info"); return; }
        TransactionRequest req = accountService.getRequests().get(r);

        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                boolean ok = false;
                if ("Deposit".equalsIgnoreCase(req.getType())) ok = accountService.deposit(req.getUsername(), req.getAmount());
                else if ("Withdraw".equalsIgnoreCase(req.getType())) ok = accountService.withdraw(req.getUsername(), req.getAmount());
                return ok;
            }

            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> {
                    try {
                        boolean ok = get();
                        if (ok) {
                            accountService.removeRequest(req);
                            loadRequestData();
                            refreshTable();
                            showDialog("Request approved and processed", "success.jpg", "Success");
                        } else {
                            showDialog("Request failed (insufficient balance or invalid)", "error.jpg", "Error");
                        }
                    } catch (Exception ex) {
                        showDialog("Error processing request", "error.jpg", "Error");
                    }
                });
            }
        }.execute();
    }

    private void rejectSelectedRequest() {
        int r = requestTable.getSelectedRow();
        if (r < 0) { showDialog("Select a request to reject", "info.jpg", "Info"); return; }
        TransactionRequest req = accountService.getRequests().get(r);

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                accountService.removeRequest(req);
                return null;
            }

            @Override
            protected void done() {
                SwingUtilities.invokeLater(() -> {
                    loadRequestData();
                    showDialog("Request rejected", "info.jpg", "Info");
                });
            }
        }.execute();
    }
}
