package com.swingdemo.jcombobox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ThemeSelector {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Theme Selector");
		frame.setSize(400,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		JPanel mainPanel = new JPanel(new FlowLayout());
		mainPanel.setBackground(Color.WHITE);
		frame.add(mainPanel, BorderLayout.CENTER);
		
		String[] themes = {"Light", "Dark", "Blue"};
		JComboBox<String> themeComboBox = new JComboBox<>(themes);
		mainPanel.add(new Label("Choose Theme"));
		mainPanel.add(themeComboBox);
		
		JButton resetButton = new JButton("Reset");
		mainPanel.add(resetButton);
		
		//event handling
		themeComboBox.addActionListener(e -> {
			String  selectedTheme = (String)themeComboBox.getSelectedItem();
			switch(selectedTheme) {
				case "Light" -> mainPanel.setBackground(Color.WHITE);
				case "Dark" -> mainPanel.setBackground(Color.DARK_GRAY);
				case "Blue" -> mainPanel.setBackground(Color.CYAN);
			}
		});
		
		resetButton.addActionListener(e -> {
			themeComboBox.setSelectedIndex(0);
			mainPanel.setBackground(Color.WHITE);
		});
		
		frame.setVisible(true);
	}

}
