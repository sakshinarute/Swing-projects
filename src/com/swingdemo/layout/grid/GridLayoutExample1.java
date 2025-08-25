package com.swingdemo.layout.grid;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GridLayoutExample1 {

	public static void main(String[] args) {
		 JFrame frame = new JFrame("GridLayout Example");
	        frame.setSize(300, 300);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        frame.setLayout(new GridLayout(3, 3)); // 3 rows, 3 cols

	        for (int i = 1; i <= 9; i++) {
	            frame.add(new JButton("Button " + i));
	        }

	        frame.setVisible(true);
	}

}
