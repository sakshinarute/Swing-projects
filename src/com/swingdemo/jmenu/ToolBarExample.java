package com.swingdemo.jmenu;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class ToolBarExample {

	public static void main(String[] args) {
		 JFrame frame = new JFrame("JToolBar Demo");
	        frame.setSize(500, 300);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(new BorderLayout());

	        // Toolbar at the top
	        JToolBar tb = new JToolBar("Main Tools");
	        tb.setRollover(true);       // nicer hover look
	        tb.setFloatable(true);      // allow dragging/undocking (try it!)

	        JButton newBtn  = new JButton("New");
	        newBtn.setToolTipText("Create a new file");
	        newBtn.addActionListener(e -> JOptionPane.showMessageDialog(frame, "New clicked"));

	        JButton saveBtn = new JButton("Save");
	        saveBtn.setToolTipText("Save current file");
	        saveBtn.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Save clicked"));

	        tb.add(newBtn);
	        tb.add(saveBtn);
	        tb.addSeparator();
	        JToggleButton bold = new JToggleButton("Bold");
	        bold.setToolTipText("Toggle bold");
	        tb.add(bold);

	        frame.add(tb, BorderLayout.NORTH);

	        // Some editable area to make it feel real
	        JTextArea area = new JTextArea("Try the toolbar buttons above…");
	        frame.add(new JScrollPane(area), BorderLayout.CENTER);

	        frame.setVisible(true);
	}

}
