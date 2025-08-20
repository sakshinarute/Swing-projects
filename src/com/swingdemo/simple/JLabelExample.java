package com.swingdemo.simple;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class JLabelExample {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Label Example");
		frame.setSize(500, 300);
		frame.setLayout(null);
		
		JLabel label1 = new JLabel("First Label");
		label1.setBounds(50, 50, 200, 40);
		label1.setText("Updated First Label");
		label1.setForeground(Color.BLUE);
		label1.setBackground(Color.YELLOW);
		label1.setOpaque(true);
		label1.setFont(new Font("Arial", Font.BOLD, 16));
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setVerticalAlignment(SwingConstants.CENTER);
		label1.setToolTipText("This is Label 1");
		
		frame.add(label1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
