package com.swing.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class DisplayGraphics extends JPanel {
	private static final long serialVersionUID = 1L;

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears background & prepares panel

        // 1. Text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Hello, Graphics!", 20, 20);

        // 2. Rectangle (Blue)
        g.setColor(Color.BLUE);
        g.fillRect(50, 50, 100, 100); // Filled rectangle
        g.drawRect(50, 50, 100, 100); // Rectangle border

        // 3. Circle (Red)
        g.setColor(Color.RED);
        g.fillOval(200, 50, 100, 100); // Filled circle
        g.drawOval(200, 50, 100, 100); // Circle border

        // 4. Line (Magenta)
        g.setColor(Color.MAGENTA);
        g.drawLine(50, 200, 150, 300); // Diagonal line
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Graphics Demo");
        DisplayGraphics graphics = new DisplayGraphics();
        frame.add(graphics);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
