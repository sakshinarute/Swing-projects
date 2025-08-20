package com.swingdemo.simple;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BasicFormExample {
	public static void main(String[] args) {
		JFrame f = new JFrame("Basic Form Example");
        f.setSize(600, 600);
        f.setLayout(null);

        // -------- NAME FIELD --------
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 30);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setForeground(Color.BLUE);
        nameField.setBackground(Color.WHITE);
        nameField.setToolTipText("Enter your name");

        // -------- PASSWORD FIELD --------
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 30);
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 100, 200, 30);
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setForeground(Color.BLACK);
        passField.setBackground(Color.WHITE);
        passField.setToolTipText("Enter your password");

       
        passField.setEchoChar('*');   
        
        
        // -------- GENDER RADIO BUTTONS --------
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 150, 100, 30);
        genderLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");

        male.setBounds(150, 150, 80, 30);
        female.setBounds(230, 150, 100, 30);

        male.setFont(new Font("Arial", Font.PLAIN, 14));
        female.setFont(new Font("Arial", Font.PLAIN, 14));

        // Group radio buttons 
        ButtonGroup bg = new ButtonGroup();
        bg.add(male);
        bg.add(female);

        // -------- HOBBIES CHECKBOXES --------
        JLabel hobbiesLabel = new JLabel("Hobbies:");
        hobbiesLabel.setBounds(50, 200, 100, 30);
        hobbiesLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JCheckBox reading = new JCheckBox("Reading");
        JCheckBox sports = new JCheckBox("Sports");
        JCheckBox music = new JCheckBox("Music");

        reading.setBounds(150, 200, 100, 30);
        sports.setBounds(250, 200, 100, 30);
        music.setBounds(350, 200, 100, 30);

        reading.setFont(new Font("Arial", Font.PLAIN, 14));
        sports.setFont(new Font("Arial", Font.PLAIN, 14));
        music.setFont(new Font("Arial", Font.PLAIN, 14));

        // -------- ADDRESS TEXT AREA --------
        JLabel addrLabel = new JLabel("Address:");
        addrLabel.setBounds(50, 250, 100, 30);
        addrLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JTextArea addressArea = new JTextArea();
        addressArea.setBounds(150, 250, 200, 100);
        addressArea.setFont(new Font("Arial", Font.PLAIN, 14));
        addressArea.setForeground(Color.BLACK);
        addressArea.setBackground(Color.LIGHT_GRAY);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        addressArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        addressArea.setToolTipText("Enter your address");

        // -------- BUTTONS --------
        JButton submit = new JButton("Submit");
        JButton reset = new JButton("Reset");

        submit.setBounds(150, 400, 100, 40);
        reset.setBounds(260, 400, 100, 40);

        submit.setFont(new Font("Arial", Font.BOLD, 14));
        submit.setBackground(Color.BLUE);
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);

        reset.setFont(new Font("Arial", Font.BOLD, 14));
        reset.setBackground(Color.RED);
        reset.setForeground(Color.WHITE);
        reset.setFocusPainted(false);

        
        f.add(nameLabel);
        f.add(nameField);

        f.add(passLabel);
        f.add(passField);

        f.add(genderLabel);
        f.add(male);
        f.add(female);

        f.add(hobbiesLabel);
        f.add(reading);
        f.add(sports);
        f.add(music);

        f.add(addrLabel);
        f.add(addressArea);

        f.add(submit);
        f.add(reset);

       
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
	}

}
