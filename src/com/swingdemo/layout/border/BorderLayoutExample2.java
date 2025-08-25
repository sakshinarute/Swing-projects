package com.swingdemo.layout.border;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BorderLayoutExample2 {

	public static void main(String[] args) {
		JFrame frame = new JFrame("BorderLayout Example 2");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Header", SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(new JButton("Click Me"), BorderLayout.CENTER);
        panel.add(new JLabel("Footer", SwingConstants.CENTER), BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

	}

}
