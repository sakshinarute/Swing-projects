package com.swingdemo.layout;

import javax.swing.*;
import java.awt.*;

public class LayoutSwitcherExample extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;

    public LayoutSwitcherExample() {
        setTitle("Dynamic Layout Switcher");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

       
        JPanel topPanel = new JPanel(new FlowLayout());
        JButton flowBtn = new JButton("Flow Layout");
        JButton gridBtn = new JButton("Grid Layout");
        JButton borderBtn = new JButton("Border Layout");
        JButton boxBtn = new JButton("Box Layout");

        topPanel.add(flowBtn);
        topPanel.add(gridBtn);
        topPanel.add(borderBtn);
        topPanel.add(boxBtn);

        add(topPanel, BorderLayout.NORTH);

       
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout()); 
        add(mainPanel, BorderLayout.CENTER);

        
        for (int i = 1; i <= 6; i++) {
            JLabel lbl = new JLabel("Photo " + i, SwingConstants.CENTER);
            lbl.setPreferredSize(new Dimension(100, 60));
            lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            mainPanel.add(lbl);
        }

        
        flowBtn.addActionListener(e -> switchToFlow());
        gridBtn.addActionListener(e -> switchToGrid());
        borderBtn.addActionListener(e -> switchToBorder());
        boxBtn.addActionListener(e -> switchToBox());

        setVisible(true);
    }

    private void switchToFlow() {
        mainPanel.removeAll();
        mainPanel.setLayout(new FlowLayout());
        addPhotos();
    }

    private void switchToGrid() {
        mainPanel.removeAll();
        mainPanel.setLayout(new GridLayout(2, 3, 10, 10)); // 2 rows, 3 cols, gaps
        addPhotos();
    }

    private void switchToBorder() {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JLabel("Photo 1", SwingConstants.CENTER), BorderLayout.NORTH);
        mainPanel.add(new JLabel("Photo 2", SwingConstants.CENTER), BorderLayout.SOUTH);
        mainPanel.add(new JLabel("Photo 3", SwingConstants.CENTER), BorderLayout.EAST);
        mainPanel.add(new JLabel("Photo 4", SwingConstants.CENTER), BorderLayout.WEST);
        mainPanel.add(new JLabel("Photo 5", SwingConstants.CENTER), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void switchToBox() {
        mainPanel.removeAll();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); 
        addPhotos();
    }

    private void addPhotos() {
        for (int i = 1; i <= 6; i++) {
            JLabel lbl = new JLabel("Photo " + i, SwingConstants.CENTER);
            lbl.setPreferredSize(new Dimension(100, 60));
            lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            mainPanel.add(lbl);
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        new LayoutSwitcherExample();
    }
}

