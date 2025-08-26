package com.swingdemo.jmenu;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

public class JMenuExample {

	public static void main(String[] args) {
		JFrame frame = new JFrame("JMenu Demo");
        frame.setSize(420, 220);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File");          
        file.add(new JMenuItem("New"));
        file.add(new JMenuItem("Open"));
        file.addSeparator();

        JMenu recent = new JMenu("Open Recent"); 
        recent.add(new JMenuItem("Project A"));
        recent.add(new JMenuItem("Project B"));
        file.add(recent);

        file.addSeparator();
        file.add(new JMenuItem("Exit"));

        bar.add(file);
        bar.add(new JMenu("Help"));
        frame.setJMenuBar(bar);

        frame.add(new JLabel("JMenu groups items (can have submenus)", SwingConstants.CENTER), BorderLayout.CENTER);
        frame.setVisible(true);
	}

}
