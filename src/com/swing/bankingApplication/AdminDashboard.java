package com.swing.bankingApplication;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {

    private AccountService accountService = new AccountService();
    private JTable table;
    private DefaultTableModel model;
    private JTree navTree;

    public AdminDashboard() {
        setTitle("Admin Dashboard - " + Session.loggedInUser);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        // ---------------- Menu Bar ----------------
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem logout = new JMenuItem("Logout");
        file.add(logout);
        mb.add(file);

        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        help.add(about);
        mb.add(help);
        setJMenuBar(mb);

        logout.addActionListener(a -> doLogout());
        about.addActionListener(a -> showDialog("Banking App - Demo", "info.jpg", "Info"));

        // ---------------- Top Toolbar (Add Account) ----------------
        JToolBar tb = new JToolBar();
        tb.setOpaque(false); 
        JButton btnAdd = new JButton("Add Account", loadIcon("add.png", 32, 32));
        tb.add(btnAdd);
        btnAdd.addActionListener(a -> showAddDialog());
        getContentPane().add(tb, BorderLayout.NORTH);

        // ---------------- Navigation Tree ----------------
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Navigation");
        DefaultMutableTreeNode accNode = new DefaultMutableTreeNode("Accounts");
        DefaultMutableTreeNode sav = new DefaultMutableTreeNode("Savings");
        DefaultMutableTreeNode cur = new DefaultMutableTreeNode("Current");
        root.add(accNode);
        accNode.add(sav);
        accNode.add(cur);

        navTree = new JTree(root);
        navTree.setRootVisible(false);
        navTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode sel = (DefaultMutableTreeNode) navTree.getLastSelectedPathComponent();
            if (sel == null) return;
            String val = sel.toString();
            if (val.equals("Savings") || val.equals("Current")) filterByType(val);
            else refreshTable();
        });

        // ---------------- Table ----------------
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

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(navTree), new JScrollPane(table));
        split.setDividerLocation(220);
        getContentPane().add(split, BorderLayout.CENTER);

        // ---------------- Bottom Panel ----------------
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setOpaque(false); 
        JButton btnShowUsers = new JButton("Show Usernames", loadIcon("info.jpg", 24, 24));
        JButton btnDelete = new JButton("Delete Selected", loadIcon("delete.jpg", 24, 24));
        JButton btnRefresh = new JButton("Refresh", loadIcon("refresh.png", 24, 24));
        JButton btnSave = new JButton("Save Changes", loadIcon("save.jpg", 24, 24));
        bottom.add(btnShowUsers);
        bottom.add(btnDelete);
        bottom.add(btnRefresh);
        bottom.add(btnSave);
        getContentPane().add(bottom, BorderLayout.SOUTH);

        // ---------------- Button Actions ----------------
        btnShowUsers.addActionListener(a -> showUsernames());
        btnDelete.addActionListener(a -> deleteSelected());
        btnRefresh.addActionListener(a -> refreshTable());
        btnSave.addActionListener(a -> saveChanges());

        refreshTable();
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

    // ---------------- Dialog Helpers ----------------
    private void showDialog(String message, String iconFile, String title) {
        ImageIcon icon = loadIcon(iconFile, 64, 64);
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE, icon);
    }

    private int showConfirmDialog(String message, String iconFile, String title) {
        ImageIcon icon = loadIcon(iconFile, 64, 64);
        return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
    }

    // ---------------- Table Operations ----------------
    private void refreshTable() {
        model.setRowCount(0);
        List<Account> all = accountService.getAllAccounts();
        for (Account a : all) {
            model.addRow(new Object[]{a.getUsername(), a.getOwnerName(), a.getGender(), a.getAccountType(), a.getBalance(), a.isSmsAlerts()});
        }
    }

    private void filterByType(String type) {
        model.setRowCount(0);
        List<Account> all = accountService.getAllAccounts();
        for (Account a : all) {
            if (a.getAccountType().equalsIgnoreCase(type)) {
                model.addRow(new Object[]{a.getUsername(), a.getOwnerName(), a.getGender(), a.getAccountType(), a.getBalance(), a.isSmsAlerts()});
            }
        }
    }

    private void deleteSelected() {
        int r = table.getSelectedRow();
        if (r < 0) {
            showDialog("Select a row to delete", "info.jpg", "Info");
            return;
        }
        String uname = model.getValueAt(r, 0).toString();
        int conf = showConfirmDialog("Delete account for '" + uname + "'?", "warning.jpg", "Confirm Delete");
        if (conf == JOptionPane.YES_OPTION) {
            accountService.deleteByUsername(uname);
            refreshTable();
            showDialog("Deleted account for " + uname, "success.jpg", "Success");
        }
    }

    private void saveChanges() {
        for (int i = 0; i < model.getRowCount(); i++) {
            String username = (String) model.getValueAt(i, 0);
            Account acc = accountService.findByUsername(username);
            if (acc != null) {
                acc.setOwnerName((String) model.getValueAt(i, 1));
                acc.setGender((String) model.getValueAt(i, 2));
                acc.setAccountType((String) model.getValueAt(i, 3));
                acc.setBalance((Double) model.getValueAt(i, 4));
                acc.setSmsAlerts((Boolean) model.getValueAt(i, 5));
            }
        }
        showDialog("Changes saved successfully!", "success.jpg", "Success");
    }

    private void showUsernames() {
        StringBuilder sb = new StringBuilder();
        for (Account a : accountService.getAllAccounts()) sb.append(a.getUsername()).append("\n");
        JTextArea ta = new JTextArea(sb.toString());
        ta.setEditable(false);
        JOptionPane.showMessageDialog(this, ta, "Usernames", JOptionPane.INFORMATION_MESSAGE, loadIcon("info.jpg", 64, 64));
    }

    private void doLogout() {
        int choice = showConfirmDialog("Logout now?", "warning.jpg", "Logout");
        if (choice == JOptionPane.YES_OPTION) {
            Session.loggedInUser = null;
            Session.isAdmin = false;
            dispose();
            new LoginFrame();
        }
    }

    // ---------------- Add Account Dialog ----------------
    private void showAddDialog() {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JPanel row1 = new JPanel(new GridLayout(2, 2, 6, 6));
        JTextField txtUsername = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        row1.add(new JLabel("Username:")); row1.add(txtUsername);
        row1.add(new JLabel("Password:")); row1.add(txtPassword);

        JPanel row2 = new JPanel(new GridLayout(2, 2, 6, 6));
        JTextField txtName = new JTextField();
        JRadioButton rMale = new JRadioButton("Male");
        JRadioButton rFemale = new JRadioButton("Female");
        JRadioButton rOther = new JRadioButton("Other");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rMale); bg.add(rFemale); bg.add(rOther);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(rMale); genderPanel.add(rFemale); genderPanel.add(rOther);
        row2.add(new JLabel("Owner Name:")); row2.add(txtName);
        row2.add(new JLabel("Gender:")); row2.add(genderPanel);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.add(new JLabel("Account Type:"));
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Savings", "Current"});
        row3.add(typeBox);

        JPanel row4 = new JPanel(new GridLayout(1, 2, 6, 6));
        JTextField txtBalance = new JTextField();
        row4.add(new JLabel("Initial Balance:")); row4.add(txtBalance);

        JPanel row5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JCheckBox chkSms = new JCheckBox("Subscribe to SMS Alerts?");
        row5.add(chkSms);

        form.add(row1); form.add(row2); form.add(row3); form.add(row4); form.add(row5);

        int res = JOptionPane.showConfirmDialog(this, form, "Add Account", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            String uname = txtUsername.getText().trim();
            String pwd = new String(txtPassword.getPassword()).trim();
            String name = txtName.getText().trim();
            String gender = rMale.isSelected() ? "Male" : rFemale.isSelected() ? "Female" : rOther.isSelected() ? "Other" : "Unspecified";
            String type = (String) typeBox.getSelectedItem();
            double bal = 0;
            try { bal = Double.parseDouble(txtBalance.getText().trim()); } catch (Exception ex) {}
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
        }
    }
}
