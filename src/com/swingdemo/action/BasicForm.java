package com.swingdemo.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BasicForm {

	public static void main(String[] args) {
		JFrame f = new JFrame("Basic Form Example");
        f.setSize(600, 700);
        f.setLayout(null);

        // -------- NAME FIELD --------
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 30);

        // -------- PASSWORD FIELD --------
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 30);
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 100, 200, 30);
        passField.setEchoChar('*');

        // -------- GENDER RADIO BUTTONS --------
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 150, 100, 30);

        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");
        male.setBounds(150, 150, 80, 30);
        female.setBounds(230, 150, 100, 30);

        ButtonGroup bg = new ButtonGroup();
        bg.add(male);
        bg.add(female);

        // -------- HOBBIES CHECKBOXES --------
        JLabel hobbiesLabel = new JLabel("Hobbies:");
        hobbiesLabel.setBounds(50, 200, 100, 30);

        JCheckBox reading = new JCheckBox("Reading");
        JCheckBox sports = new JCheckBox("Sports");
        JCheckBox music = new JCheckBox("Music");

        reading.setBounds(150, 200, 100, 30);
        sports.setBounds(250, 200, 100, 30);
        music.setBounds(350, 200, 100, 30);

        // -------- ADDRESS TEXT AREA --------
        JLabel addrLabel = new JLabel("Address:");
        addrLabel.setBounds(50, 250, 100, 30);

        JTextArea addressArea = new JTextArea();
        addressArea.setBounds(150, 250, 200, 100);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        addressArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        JScrollPane scroll = new JScrollPane(addressArea);
        scroll.setBounds(150, 250, 200, 100); 

        // -------- BUTTONS --------
        JButton submit = new JButton("Submit");
        JButton reset = new JButton("Reset");
        
        submit.setBounds(150, 370, 100, 40);  
        reset.setBounds(260, 370, 100, 40);

        // -------- OUTPUT AREA --------
        JLabel outputLabel = new JLabel("Output:");
        outputLabel.setBounds(50, 430, 100, 30);   
        outputLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JTextArea outputArea = new JTextArea();
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        outputArea.setEditable(false);   
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        // Wrap inside JScrollPane for scrolling
        JScrollPane outputScroll = new JScrollPane(outputArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outputScroll.setBounds(50, 460, 500, 150);

        // -------- ACTION LISTENERS --------
        submit.addActionListener(e -> {
            String name = nameField.getText();
            String password = new String(passField.getPassword());
            String gender = male.isSelected() ? "Male" : (female.isSelected() ? "Female" : "Not Selected");

            StringBuilder hobbies = new StringBuilder();
            if (reading.isSelected()) {
                if (hobbies.length() > 0) hobbies.append(", ");
                hobbies.append("Reading");
            }
            if (sports.isSelected()) {
                if (hobbies.length() > 0) hobbies.append(", ");
                hobbies.append("Sports");
            }
            if (music.isSelected()) {
                if (hobbies.length() > 0) hobbies.append(", ");
                hobbies.append("Music");
            }

            String address = addressArea.getText();

            // Display data inside outputArea
            outputArea.setText(
                    "Name: " + name +
                    "\nPassword: " + password +
                    "\nGender: " + gender +
                    "\nHobbies: " + hobbies +
                    "\nAddress: " + address
            );
        });

        reset.addActionListener(e -> {
            nameField.setText("");
            passField.setText("");
            bg.clearSelection();
            reading.setSelected(false);
            sports.setSelected(false);
            music.setSelected(false);
            addressArea.setText("");
            outputArea.setText(""); // clear output
        });

        // Mouse hover effect on Submit button
        submit.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { submit.setBackground(Color.GREEN); }
            public void mouseExited(MouseEvent e) { submit.setBackground(null); }
        });

        // Add components
        f.add(nameLabel); f.add(nameField);
        f.add(passLabel); f.add(passField);
        f.add(genderLabel); f.add(male); f.add(female);
        f.add(hobbiesLabel); f.add(reading); f.add(sports); f.add(music);
        f.add(addrLabel); f.add(addressArea);
        f.add(submit); f.add(reset);
        f.add(outputLabel); f.add(outputScroll);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
	}

}
