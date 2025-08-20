package com.swingdemo.action;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TextFieldActionExample {
	public static void main(String[] args) {
		JFrame f = new JFrame("Text Field Action Example");
		f.setSize(400,200);
		f.setLayout(null);
		
		JTextField tf = new JTextField();
		tf.setBounds(50, 50, 200, 30);
		
		JLabel label = new JLabel("Type Something and Press Enter");
		label.setBounds(50, 100, 250, 30);
		
		tf.addActionListener(e -> label.setText("You Typed: "+tf.getText()));
		
		f.add(tf);
		f.add(label);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}
