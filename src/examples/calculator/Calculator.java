package examples.calculator;

import acm.program.Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Calculator extends Program {
    private JTextField display;

    public void init() {
        setSize(500, 600);

        createTextField();
        createNumpad();

        addActionListeners();
    }

    private void createTextField() {
        display = new JTextField(11);
        display.setFont(new Font("Arial", Font.BOLD, 40));
        add(display, NORTH);
    }

    private void createNumpad() {
        JPanel numpad = new JPanel();
        numpad.setLayout(new GridLayout(4, 4));
        add(numpad, CENTER);
        String label = "789/456*123-.0=+";
        for (int i = 0; i < label.length(); i++) {
            JButton button = new JButton("" + label.charAt(i));
            button.setFont(new Font("Arial", Font.BOLD, 36));
            numpad.add(button);
        }
    }

    public void actionPerformed(ActionEvent e) {
        char cmd = e.getActionCommand().charAt(0);
        if (cmd != '=') {
            display.setText(display.getText() + cmd);
        } else {
            display.setText("" + calculate(display.getText()));
        }
    }

    private double calculate(String expression) {
        StringBuilder temp = new StringBuilder();
        ArrayList<Double> numbers = new ArrayList<>();
        ArrayList<Character> operators = new ArrayList<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (!"+-*/".contains(c + "")) {
                temp.append(expression.charAt(i));
            } else {
                numbers.add(Double.parseDouble(temp.toString()));
                operators.add(c);
                temp = new StringBuilder();
            }

        }
        numbers.add(Double.parseDouble(temp.toString()));

        return calculate(numbers, operators);
    }

    private double calculate(ArrayList<Double> numbers, ArrayList<Character> operators) {
        double result = numbers.getFirst();

        for (int i = 0; i < operators.size(); i++) {
            switch (operators.get(i)) {
                case '+':
                    result += numbers.get(i + 1);
                    break;
                case '-':
                    result -= numbers.get(i + 1);
                    break;
                case '*':
                    result *= numbers.get(i + 1);
                    break;
                case '/':
                    result /= numbers.get(i + 1);
                    break;
            }
        }

        return result;
    }
}