package com.swingdemo.layout.flow;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FlowLayoutExample2 {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Flow Example 2");
        frame.setSize(400, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new FlowLayout());

        frame.add(new JLabel("Enter Name:"));
        frame.add(new JTextField(10));
        frame.add(new JButton("Submit"));

        frame.setVisible(true);

	}

}
