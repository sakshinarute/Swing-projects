package com.swingdemo;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class JTextFieldExample {
	public static void main(String[] args) {
		JFrame frame = new JFrame("JTextField Example");
		frame.setSize(500,300);
		frame.setLayout(null);
		
		JTextField t1 = new JTextField(); 
		JTextField t2 = new JTextField("Default Text");
        
        t1.setBounds(50, 50, 200, 30);
        t2.setBounds(50, 120, 200, 30);
        
        t1.setText("Hello World");                
        t1.setForeground(Color.BLUE);             
        t1.setBackground(Color.LIGHT_GRAY);       
        t1.setFont(new Font("Arial", Font.BOLD, 16)); 
        t1.setHorizontalAlignment(JTextField.CENTER); 
        t1.setEditable(true);                     
        t1.setEnabled(true);                      
        t1.setToolTipText("Enter text here");     
        t1.setColumns(15);
        
        t2.setText("Updated Text");               
        t2.setForeground(Color.RED);              
        t2.setFont(new Font("Serif", Font.ITALIC, 14));
        t2.setHorizontalAlignment(JTextField.RIGHT);
        t2.setEditable(false);                    
        t2.setToolTipText("This field is read-only");
        
        frame.add(t1);
        frame.add(t2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}

}
