package com.swingdemo.jtree;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class JTreeExample {

	public static void main(String[] args) {
		JFrame frame = new JFrame("JTree Example - Company Structure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

       
        DefaultMutableTreeNode company = new DefaultMutableTreeNode("Company");

        // IT Department
        DefaultMutableTreeNode itDept = new DefaultMutableTreeNode("IT Department");
        itDept.add(new DefaultMutableTreeNode("Developer"));
        itDept.add(new DefaultMutableTreeNode("Tester"));

        // HR Department
        DefaultMutableTreeNode hrDept = new DefaultMutableTreeNode("HR Department");
        hrDept.add(new DefaultMutableTreeNode("Recruiter"));
        hrDept.add(new DefaultMutableTreeNode("Payroll"));

        // Add departments to company
        company.add(itDept);
        company.add(hrDept);

        // JTree using the root node
        JTree tree = new JTree(company);

        
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }

       
        JLabel selectedLabel = new JLabel("Select a node");

        // event handling
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode =
                (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode != null) {
                selectedLabel.setText("Selected: " + selectedNode.getUserObject());
            }
        });


        
        JScrollPane scrollPane = new JScrollPane(tree);

        
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(selectedLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
	}

}
