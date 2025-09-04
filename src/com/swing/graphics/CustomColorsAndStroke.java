package com.swing.graphics;

import java.awt.*;
import javax.swing.*;

public class CustomColorsAndStroke extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast to Graphics2D for advanced features
        Graphics2D g2 = (Graphics2D) g;

        // 1. Custom Color using RGB
        Color customColor = new Color(128, 0, 128); 
        g2.setColor(customColor);
        g2.fillRect(50, 50, 120, 80);

        // 2. Thick Line using Stroke
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(8)); // 8px thickness
        g2.drawLine(50, 180, 250, 180);

        // 3. Dashed Line using Stroke
        float[] dashPattern = {10, 10}; // 10px dash, 10px gap
        g2.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 0, dashPattern, 0));
        g2.setColor(Color.RED);
        g2.drawLine(50, 220, 250, 220);

        // 4. Oval with Custom Color
        Color customBlue = Color.decode("#1E90FF"); // Hex color
        g2.setColor(customBlue);
        g2.fillOval(200, 50, 100, 100);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Custom Colors & Stroke");
        frame.add(new CustomColorsAndStroke());
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

