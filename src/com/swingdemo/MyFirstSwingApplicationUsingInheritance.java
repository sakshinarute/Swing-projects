package com.swingdemo;

import javax.swing.JFrame;

public class MyFirstSwingApplicationUsingInheritance extends JFrame {
	
	private static final long serialVersionUID = 1L;


	public static void main(String[] args) {
		new MyFirstSwingApplicationUsingInheritance();
	}
	
	
	MyFirstSwingApplicationUsingInheritance(){
		super("My First Swing App (Inheritance)");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
}
