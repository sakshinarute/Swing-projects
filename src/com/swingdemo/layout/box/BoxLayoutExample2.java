package com.swingdemo.layout.box;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BoxLayoutExample2 {

	public static void main(String[] args) {
		JFrame frame = new JFrame("BoxLayout Horizontal");
        frame.setSize(400, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS)); // Horizontal

        panel.add(new JButton("Yes"));
        panel.add(Box.createHorizontalStrut(20)); // space
        panel.add(new JButton("No"));

        frame.add(panel);
        frame.setVisible(true);
	}

}
