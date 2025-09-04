package com.swing.graphics;


import java.awt.*;
import javax.swing.*;

public class SimpleGradientExample extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast to Graphics2D for gradient
        Graphics2D g2 = (Graphics2D) g;

        // Create a gradient from blue to white
        GradientPaint gradient = new GradientPaint(0, 0, Color.BLUE, getWidth(), getHeight(), Color.WHITE);
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw text on top
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 24));
        g2.drawString("Gradient Background", 50, 100);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Gradient Example");
        frame.add(new SimpleGradientExample());
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
