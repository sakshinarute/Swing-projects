package com.swing.fileexplorer;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class FileExplorer extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;

    public FileExplorer() {
        super("File Explorer with JFileChooser");

       
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        
        fileChooser = new JFileChooser();

        
        JButton openBtn = new JButton("Open File");
        JButton saveBtn = new JButton("Save File");
        JButton cancelBtn = new JButton("Cancel");

       
        openBtn.addActionListener(e -> openFile());
        saveBtn.addActionListener(e -> saveFile());
        cancelBtn.addActionListener(e -> System.exit(0));

        
        JPanel panel = new JPanel();
        panel.add(openBtn);
        panel.add(saveBtn);
        panel.add(cancelBtn);

        
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void openFile() {
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.setText(""); // clear old content
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(textArea.getText());
                JOptionPane.showMessageDialog(this, "File saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
    	new FileExplorer();
    }
}

