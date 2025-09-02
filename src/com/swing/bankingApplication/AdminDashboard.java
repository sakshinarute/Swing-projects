package com.swing.bankingApplication;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
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

        // menu bar
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem logout = new JMenuItem("Logout");
        file.add(logout);
        mb.add(file);
        setJMenuBar(mb);
        logout.addActionListener(a -> doLogout());

        // toolbar top
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);
        JButton btnAdd = new JButton("Add Account", loadIcon("add.png", 28, 28));
        JButton btnDelete = new JButton("Delete Selected", loadIcon("delete.jpg", 28, 28));
        JButton btnRefresh = new JButton("Refresh", loadIcon("refresh.png", 28, 28));
        JButton btnSave = new JButton("Save Changes", loadIcon("save.jpg", 28, 28));

        tb.add(btnAdd); tb.add(btnDelete); tb.add(btnRefresh); tb.add(btnSave);
        getContentPane().add(tb, BorderLayout.NORTH);

        // nav tree left
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Navigation");
        DefaultMutableTreeNode accNode = new DefaultMutableTreeNode("Accounts");
        root.add(accNode);
        accNode.add(new DefaultMutableTreeNode("Savings"));
        accNode.add(new DefaultMutableTreeNode("Current"));

        navTree = new JTree(root);
        navTree.setRootVisible(false);
        navTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode sel = (DefaultMutableTreeNode) navTree.getLastSelectedPathComponent();
            if (sel == null) return;
            String val = sel.toString();
            if ("Savings".equals(val) || "Current".equals(val)) filterByType(val);
            else refreshTable();
        });

        // account table center top
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

        // request table center bottom
        String[] reqCols = {"Username", "Type", "Amount"};
        requestModel = new DefaultTableModel(reqCols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
            @Override public Class<?> getColumnClass(int col) {
                if (col == 2) return Double.class;
                return String.class;
            }
        };
        requestTable = new JTable(requestModel);

        JScrollPane acctScroll = new JScrollPane(table);
        JScrollPane reqScroll = new JScrollPane(requestTable);
        JSplitPane vert = new JSplitPane(JSplitPane.VERTICAL_SPLIT, acctScroll, reqScroll);
        vert.setDividerLocation(360);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(navTree), vert);
        split.setDividerLocation(220);
        getContentPane().add(split, BorderLayout.CENTER);

        // bottom buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnShowUsers = new JButton("Show Usernames", loadIcon("info.jpg", 22, 22));
        JButton btnApprove = new JButton("Approve Request", loadIcon("success.jpg", 22,22));
        JButton btnReject = new JButton("Reject Request", loadIcon("delete.jpg",22,22));
        bottom.add(btnShowUsers);
        bottom.add(btnApprove);
        bottom.add(btnReject);
        getContentPane().add(bottom, BorderLayout.SOUTH);

        // action wiring
        btnAdd.addActionListener(a -> showAddDialog());
        btnDelete.addActionListener(a -> deleteSelected());
        btnRefresh.addActionListener(a -> refreshTable());
        btnSave.addActionListener(a -> saveChanges());

        btnShowUsers.addActionListener(a -> showUsernames());
        btnApprove.addActionListener(a -> approveSelectedRequest());
        btnReject.addActionListener(a -> rejectSelectedRequest());

        // initial load
        refreshTable();
        loadRequestData();

        setVisible(true);
    }

    // local icon loader
    private ImageIcon loadIcon(String name, int w, int h) {
        try {
            java.net.URL url = getClass().getResource("/com/swing/bankingApplication/icons/" + name);
            if (url == null) return null;
            ImageIcon ic = new ImageIcon(url);
            Image img = ic.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) { return null; }
    }

    // dialog helpers using icons
    private void showDialog(String message, String iconName, String title) {
        ImageIcon icon = loadIcon(iconName, 64, 64);
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE, icon);
    }
    private int showConfirm(String message, String iconName, String title) {
        ImageIcon icon = loadIcon(iconName, 64, 64);
        return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
    }

    // table operations
    public void refreshTable() {
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

    private void loadRequestData() {
        requestModel.setRowCount(0);
        for (TransactionRequest r : accountService.getRequests()) {
            requestModel.addRow(new Object[]{r.getUsername(), r.getType(), r.getAmount()});
        }
    }

    private void approveSelectedRequest() {
        int r = requestTable.getSelectedRow();
        if (r < 0) { showDialog("Select a request to approve", "info.jpg", "Info"); return; }
        TransactionRequest req = accountService.getRequests().get(r);
        boolean ok = false;
        if ("Deposit".equalsIgnoreCase(req.getType())) ok = accountService.deposit(req.getUsername(), req.getAmount());
        else if ("Withdraw".equalsIgnoreCase(req.getType())) ok = accountService.withdraw(req.getUsername(), req.getAmount());

        if (ok) {
            accountService.removeRequest(req);
            loadRequestData();
            refreshTable();
            showDialog("Request approved and processed", "success.jpg", "Success");
        } else {
            showDialog("Request failed (insufficient balance or invalid)", "error.jpg", "Error");
        }
    }

    private void rejectSelectedRequest() {
        int r = requestTable.getSelectedRow();
        if (r < 0) { showDialog("Select a request to reject", "info.jpg", "Info"); return; }
        TransactionRequest req = accountService.getRequests().get(r);
        accountService.removeRequest(req);
        loadRequestData();
        showDialog("Request rejected", "info.jpg", "Info");
    }
}
