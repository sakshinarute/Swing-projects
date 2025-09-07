package com.swing.multithreading;

import javax.swing.*;
import java.util.List;

public class SwingWorkerExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("SwingWorker Example");
        JButton button = new JButton("Start Task");
        JLabel label = new JLabel("Task not started");

        button.addActionListener(e -> {
            SwingWorker<Void, String> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    for (int i = 1; i <= 5; i++) {
                        Thread.sleep(1000); // Simulate a long task
                        publish("Step " + i + " completed");
                    }
                    return null;
                }

                @Override
                protected void process(List<String> chunks) {
                    label.setText(chunks.get(chunks.size() - 1)); // Update UI
                }

                @Override
                protected void done() {
                    label.setText("Task completed!");
                }
            };
            worker.execute();
        });

        frame.add(button, "North");
        frame.add(label, "Center");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
