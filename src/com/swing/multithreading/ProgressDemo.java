package com.swing.multithreading;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProgressDemo {
    private JFrame frame;
    private JProgressBar progressBar;
    private JButton startBtn, cancelBtn;
    private SwingWorker<Void, Integer> worker;

    public ProgressDemo() {
        frame = new JFrame("SwingWorker Progress Demo");
        progressBar = new JProgressBar(0, 100);
        startBtn = new JButton("Start");
        cancelBtn = new JButton("Cancel");
        cancelBtn.setEnabled(false);

        startBtn.addActionListener(e -> startWork());
        cancelBtn.addActionListener(e -> {
            if (worker != null) worker.cancel(true);
        });

        JPanel p = new JPanel();
        p.add(startBtn); p.add(cancelBtn); p.add(progressBar);

        frame.add(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startWork() {
        startBtn.setEnabled(false);
        cancelBtn.setEnabled(true);

        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100 && !isCancelled(); i++) {
                    Thread.sleep(40); // simulate work
                    setProgress(i);  // fires property change
                    publish(i);      // optional: immediate updates to process()
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int latest = chunks.get(chunks.size() - 1);
                progressBar.setValue(latest);
            }

            @Override
            protected void done() {
                startBtn.setEnabled(true);
                cancelBtn.setEnabled(false);
                try {
                    if (!isCancelled()) get(); // may throw ExecutionException
                    JOptionPane.showMessageDialog(frame, "Completed");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((Integer) evt.getNewValue());
            }
        });

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProgressDemo::new);
    }
}

