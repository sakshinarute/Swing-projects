package com.swingdemo.simple;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class JTextAreaExample {
	public static void main(String[] args) {
		JFrame f = new JFrame("JTextArea Example");
        f.setSize(600, 400);
        f.setLayout(null); 

       
        JTextArea area1 = new JTextArea();                     
        JTextArea area2 = new JTextArea("Default multi-line text"); 

        
        area1.setBounds(50, 50, 200, 100);
        area2.setBounds(300, 50, 200, 100);

       
        area1.setText("Hello, this is JTextArea.\nYou can type multiple lines.");
        area1.setForeground(Color.BLUE);                       
        area1.setBackground(Color.LIGHT_GRAY);                 
        area1.setFont(new Font("Arial", Font.BOLD, 14));       
        area1.setLineWrap(true);                               
        area1.setWrapStyleWord(true);                          
        area1.setEditable(true);                               
        area1.setEnabled(true);                                
        area1.setToolTipText("Enter your comments here");      
        area1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 
        area1.setMargin(new Insets(10, 10, 10, 10));           

        
        area2.setText("This is a read-only text area.\nUser cannot edit this.");
        area2.setForeground(Color.RED);
        area2.setFont(new Font("Serif", Font.ITALIC, 16));
        area2.setEditable(false);                              
        area2.setLineWrap(true);
        area2.setWrapStyleWord(true);
        area2.setToolTipText("This area is read-only");

        
        
        f.add(area1);
        f.add(area2);
        

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
	}
}
