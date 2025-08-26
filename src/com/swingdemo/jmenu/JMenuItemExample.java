package com.swingdemo.jmenu;

import java.awt.BorderLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class JMenuItemExample {

	public static void main(String[] args) {
		JFrame frame = new JFrame("JMenuItem Demo");
        frame.setSize(460, 240);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        frame.add(new JScrollPane(area), BorderLayout.CENTER);

        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.setMnemonic('F'); // Alt+F opens File menu

        JMenuItem newItem  = new JMenuItem("New");
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        newItem.addActionListener(e -> area.setText("New clicked\n"));

        JMenuItem openItem = new JMenuItem("Open");
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        openItem.addActionListener(e -> area.append("Open clicked\n"));

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> frame.dispose());

        file.add(newItem);
        file.add(openItem);
        file.addSeparator();
        file.add(exitItem);

        bar.add(file);
        frame.setJMenuBar(bar);

        frame.setVisible(true);

	}

}
