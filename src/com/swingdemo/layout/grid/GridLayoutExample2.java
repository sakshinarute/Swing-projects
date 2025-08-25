package com.swingdemo.layout.grid;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GridLayoutExample2 {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Calculator Grid");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new GridLayout(4, 4, 10, 10)); // gaps added (hgap=10, vgap=10)

        String[] buttons = {"7","8","9","/",
                            "4","5","6","*",
                            "1","2","3","-",
                            "0",".","=","+"};

        for (String b : buttons) {
            frame.add(new JButton(b));
        }

        frame.setVisible(true);
	}

}
