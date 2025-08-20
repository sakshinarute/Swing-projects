package com.swingdemo.action;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class JTextAreaActionExample {
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setSize(400,300);
		f.setLayout(null);
		
		JTextArea ta = new JTextArea();
		ta.setBounds(50, 50, 200, 100);
		
		JButton b = new JButton("Show Text");
		b.setBounds(50, 170, 120, 30);
		
		JLabel label = new JLabel("Output will Appear Here");
		label.setBounds(50, 220, 250, 30);
		
		b.addActionListener(e -> label.setText("Typed: "+ta.getText()));
		
		f.add(ta);
		f.add(b);
		f.add(label);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
	}

}
