package com.swingdemo.jmenu;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;

public class JMenuBarExample {

	public static void main(String[] args) {
		JFrame frame = new JFrame("JMenuBar Demo");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JMenuBar bar = new JMenuBar();            
        bar.add(new JMenu("File"));               
        bar.add(new JMenu("Help"));
        frame.setJMenuBar(bar);                   

        frame.add(new JLabel("JMenuBar holds menus", SwingConstants.CENTER), BorderLayout.CENTER);
        frame.setVisible(true);
	}

}
