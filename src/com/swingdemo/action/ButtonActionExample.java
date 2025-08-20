package com.swingdemo.action;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class ButtonActionExample {
	public static void main(String[] args) {
		JFrame f = new JFrame("Button Action Example");
		f.setSize(400,300);
		f.setLayout(null);
		
		
		JTextField tf = new JTextField("Waiting for button click...");
		tf.setBounds(50, 50, 200, 30);
		
		JButton b = new JButton("Click Me");
		b.setBounds(50, 100, 100, 30);
		
		b.addActionListener(e -> tf.setText("Button was Clicked"));
		
		f.add(tf);
		f.add(b);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
