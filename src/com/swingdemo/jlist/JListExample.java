package com.swingdemo.jlist;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JListExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JList Example");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        
        JPanel panel = new JPanel(new BorderLayout());

       
        String[] languages = {"Java", "Python", "C++", "JavaScript", "Kotlin"};
        JList<String> languageList = new JList<>(languages);
        languageList.setVisibleRowCount(5);
        languageList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
        JScrollPane scrollPane = new JScrollPane(languageList);
        panel.add(scrollPane, BorderLayout.CENTER);

        
        JLabel resultLabel = new JLabel("Select your favorite languages");
        panel.add(resultLabel, BorderLayout.NORTH);

       
        JButton showButton = new JButton("Show Selected");
        showButton.addActionListener(e -> {
            List<String> selected = languageList.getSelectedValuesList();
            if (selected.isEmpty()) {
                resultLabel.setText("No language selected!");
            } else {
                resultLabel.setText("Selected Languages: " + selected);
            }
        });

        panel.add(showButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }
}
