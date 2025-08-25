package com.swingdemo.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleCalculator extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JTextField display;
    private StringBuilder input;

    public SimpleCalculator() {
        setTitle("Calculator");
        setSize(350, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null); 

        input = new StringBuilder();

        
        display = new JTextField();
        display.setBounds(10, 10, 310, 50);
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        add(display);

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(10, 70, 310, 250);
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.addActionListener(this);
            buttonPanel.add(btn);
        }
        add(buttonPanel);

        
        JButton clearBtn = new JButton("Clear");
        clearBtn.setBounds(10, 330, 100, 50);
        clearBtn.setFont(new Font("Arial", Font.BOLD, 14));
        clearBtn.addActionListener(e -> {
            input.setLength(0);
            display.setText("");
        });
        add(clearBtn);

        JButton deleteBtn = new JButton("Del");
        deleteBtn.setBounds(120, 330, 100, 50);
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 14));
        deleteBtn.addActionListener(e -> {
            if (input.length() > 0) {
                input.deleteCharAt(input.length() - 1);
                display.setText(input.toString());
            }
        });
        add(deleteBtn);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = ((JButton) e.getSource()).getText();

        if (cmd.equals("=")) {
            try {
                double result = evaluateExpression(input.toString());
                display.setText(String.valueOf(result));
                input.setLength(0);
                input.append(result);
            } catch (Exception ex) {
                display.setText("Error");
                input.setLength(0);
            }
        } else {
            input.append(cmd);
            display.setText(input.toString());
        }
    }

    
    private double evaluateExpression(String expr) {
        char[] tokens = expr.toCharArray();
        double num1 = 0, num2 = 0;
        char operator = ' ';
        StringBuilder number = new StringBuilder();

        for (char ch : tokens) {
            if (Character.isDigit(ch) || ch == '.') {
                number.append(ch);
            } else {
                if (operator == ' ') {
                    num1 = Double.parseDouble(number.toString());
                    number.setLength(0);
                    operator = ch;
                } else {
                    num2 = Double.parseDouble(number.toString());
                    num1 = calculate(num1, num2, operator);
                    number.setLength(0);
                    operator = ch;
                }
            }
        }

        if (number.length() > 0) {
            num2 = Double.parseDouble(number.toString());
            num1 = calculate(num1, num2, operator);
        }

        return num1;
    }

    private double calculate(double a, double b, char op) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> b != 0 ? a / b : 0;
            default -> a;
        };
    }

    public static void main(String[] args) {
        new SimpleCalculator();
    }
}
