package com.swingdemo.registrationForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class RegistrationForm {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Swing Components Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout(10, 10));

        // ===== Title =====
        JLabel titleLabel = new JLabel("Registration Form", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(titleLabel, BorderLayout.NORTH);

        // ===== Form Panel =====
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField nameField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        JCheckBox music = new JCheckBox("Music");
        JCheckBox sports = new JCheckBox("Sports");
        JComboBox<String> countryBox = new JComboBox<>(new String[]{"India", "USA", "UK"});
        JTextArea addressArea = new JTextArea(3, 20);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passField);
        formPanel.add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(male);
        genderPanel.add(female);
        formPanel.add(genderPanel);
        formPanel.add(new JLabel("Interests:"));
        JPanel interestPanel = new JPanel();
        interestPanel.setLayout(new BoxLayout(interestPanel, BoxLayout.Y_AXIS));
        interestPanel.add(music);
        interestPanel.add(sports);
        formPanel.add(interestPanel);
        formPanel.add(new JLabel("Country:"));
        formPanel.add(countryBox);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(new JScrollPane(addressArea));

        // Put form panel in scroll (optional for better UI)
        JScrollPane formScroll = new JScrollPane(formPanel);

        // ===== JList on the left =====
        String[] courses = {"Java", "Python", "C++", "JavaScript"};
        JList<String> courseList = new JList<>(courses);
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listPane = new JScrollPane(courseList);

        // ===== JTree on the right =====
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Departments");
        DefaultMutableTreeNode itDept = new DefaultMutableTreeNode("IT");
        itDept.add(new DefaultMutableTreeNode("Developer"));
        itDept.add(new DefaultMutableTreeNode("Tester"));
        DefaultMutableTreeNode hrDept = new DefaultMutableTreeNode("HR");
        hrDept.add(new DefaultMutableTreeNode("Recruiter"));
        root.add(itDept);
        root.add(hrDept);
        JTree tree = new JTree(root);
        JScrollPane treePane = new JScrollPane(tree);

        // ===== Split the center for proper layout =====
        JSplitPane splitCenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPane, formScroll);
        splitCenter.setDividerLocation(150);

        JSplitPane splitRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitCenter, treePane);
        splitRight.setDividerLocation(600);

        frame.add(splitRight, BorderLayout.CENTER);

        // ===== Bottom Panel: Buttons + Table =====
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton submitBtn = new JButton("Submit");
        JButton clearBtn = new JButton("Clear");
        buttonPanel.add(submitBtn);
        buttonPanel.add(clearBtn);
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        String[] columnNames = {"Name", "Gender", "Country"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(100, 120));
        bottomPanel.add(tableScroll, BorderLayout.CENTER);


        // ===== Event Handling =====
        submitBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String gender = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "";
            String country = (String) countryBox.getSelectedItem();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (passField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(frame, "Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add row to table
            model.addRow(new Object[]{name, gender, country});

            // Show confirmation
            JOptionPane.showMessageDialog(frame,
                    "Name: " + name +
                            "\nGender: " + gender +
                            "\nCountry: " + country,
                    "Submitted", JOptionPane.INFORMATION_MESSAGE);
        });


        clearBtn.addActionListener(e -> {
            nameField.setText("");
            passField.setText("");
            genderGroup.clearSelection();
            music.setSelected(false);
            sports.setSelected(false);
            countryBox.setSelectedIndex(0);
            addressArea.setText("");
        });

        courseList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                JOptionPane.showMessageDialog(frame,
                        "Selected Course: " + courseList.getSelectedValue());
            }
        });

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    tree.getLastSelectedPathComponent();
            if (node != null) {
                JOptionPane.showMessageDialog(frame, "Selected Node: " + node.getUserObject());
            }
        });
        
        frame.add(bottomPanel, BorderLayout.SOUTH);


        frame.setVisible(true);
	}

}
