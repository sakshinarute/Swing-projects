package com.swing.graphics;


import java.awt.*;
import javax.swing.*;
import java.io.File;

public class DynamicImageExample extends JPanel {
    private Image img;

    public DynamicImageExample() {
        // Add a button to load image
        JButton loadButton = new JButton("Load Image");
        loadButton.addActionListener(e -> chooseImage());
        this.add(loadButton);
    }


    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an Image");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            img = new ImageIcon(selectedFile.getAbsolutePath()).getImage();
            repaint(); // Refresh panel to show the new image
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (img != null) {
            // Draw image at (50, 50) with fixed size
            g.drawImage(img, 50, 50, 300, 200, this);
        } else {
            g.setColor(Color.GRAY);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("No Image Loaded", 150, 150);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dynamic Image Example");
        frame.add(new DynamicImageExample());
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
