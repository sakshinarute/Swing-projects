package com.swingdemo.simple;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

public class JButtonExample {
	public static void main(String[] args) {
	            JFrame frame = new JFrame("JButton Example");
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setSize(400, 300);
	            frame.setLayout(new FlowLayout());

	            // 1. Create a button using constructor with text
	            JButton btn1 = new JButton("Click Me");

	            // 2. Set text explicitly (optional here, already set in constructor)
	            btn1.setText("Submit");

	            // 3. Change text color (foreground)
	            btn1.setForeground(Color.WHITE);

	            // 4. Change background color
	            btn1.setBackground(Color.BLUE);

	            // 5. Change font
	            btn1.setFont(new Font("Arial", Font.BOLD, 16));

	            // 6. Set border
	            btn1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

	            // 7. Enable/disable the button (true means enabled)
	            btn1.setEnabled(true);

	            // 8. Set focus painted (true → border highlight on focus, false → no focus box)
	            btn1.setFocusPainted(false);

	            // 9. Horizontal alignment (LEFT, CENTER, RIGHT)
	            btn1.setHorizontalAlignment(SwingConstants.CENTER);

	            // 10. Vertical alignment (TOP, CENTER, BOTTOM)
	            btn1.setVerticalAlignment(SwingConstants.CENTER);

	            frame.add(btn1);

	            frame.setVisible(true);
	        
	}

}
