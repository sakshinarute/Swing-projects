package com.swing.graphics;


import java.awt.*;
import javax.swing.*;

public class SimpleImageExample extends JPanel {
    private Image img;

    public SimpleImageExample() {
        // Load image from an absolute local path
        img = new ImageIcon("C:\\Users\\sakshi_narute\\Downloads\\icons\\swing.jpg").getImage();
  
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the image
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);

        // Draw text below image
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Image Example");
        frame.add(new SimpleImageExample());
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

