import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class Calculator extends JFrame {
    private enum action {NONE, PLUS, MINUS, MULTIPLY, DIVISION}

    private static action currentMode;

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        NumberFormat digFormat = NumberFormat.getIntegerInstance();

        NumberFormatter numberFormatter = new NumberFormatter(digFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);


        JFormattedTextField firstDigit = new JFormattedTextField(numberFormatter);
        firstDigit.setBounds(5,5,70,70);
        firstDigit.setHorizontalAlignment(JTextField.CENTER);
        firstDigit.setFont(new Font("Fira Code", Font.PLAIN, 12));
        panel.add(firstDigit);

        JTextField operator = new JTextField(20);
        operator.setBounds(80, 5, 70, 70);
        operator.setHorizontalAlignment(JTextField.CENTER);
        operator.setEditable(false);
        panel.add(operator);

        JFormattedTextField secondDigit = new JFormattedTextField(numberFormatter);
        secondDigit.setBounds(155,5,70,70);
        secondDigit.setHorizontalAlignment(JTextField.CENTER);
        secondDigit.setFont(new Font("Fira Code", Font.PLAIN, 12));
        panel.add(secondDigit);

        JTextField equalSign = new JTextField(20);
        equalSign.setBounds(230, 5, 70, 70);
        equalSign.setHorizontalAlignment(JTextField.CENTER);
        equalSign.setEditable(false);
        equalSign.setText("=");
        panel.add(equalSign);

        JTextField result = new JTextField(20);
        result.setBounds(305, 5, 70, 70);
        result.setHorizontalAlignment(JTextField.CENTER);
        result.setEditable(false);
        panel.add(result);

        JButton plusButton = new JButton("+");
        plusButton.setBounds(5, 80, 70, 70);
        panel.add(plusButton);

        JButton minusButton = new JButton("-");
        minusButton.setBounds(80, 80, 70, 70);
        panel.add(minusButton);

        JButton multiplyButton = new JButton("*");
        multiplyButton.setBounds(155, 80, 70, 70);
        panel.add(multiplyButton);

        JButton divisionButton = new JButton("/");
        divisionButton.setBounds(230, 80, 70, 70);
        panel.add(divisionButton);

        JButton submitButton = new JButton("OK");
        submitButton.setBounds(305, 80, 70, 70);
        panel.add(submitButton);

        plusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                operator.setText("+");
                currentMode = action.PLUS;
            }
        });

        minusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                operator.setText("-");
                currentMode = action.MINUS;
            }
        });

        multiplyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                operator.setText("*");
                currentMode = action.MULTIPLY;
            }
        });

        divisionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                operator.setText("/");
                currentMode = action.DIVISION;
            }
        });

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int first, second, res;
                    first = Integer.parseInt(firstDigit.getText().replaceAll(",", ""));
                    second = Integer.parseInt(secondDigit.getText().replaceAll(",", ""));
                    switch (currentMode) {
                        case PLUS:
                            res = first + second;
                            result.setText(Integer.toString(res));
                            break;
                        case MINUS:
                            res = first - second;
                            result.setText(Integer.toString(res));
                            break;
                        case DIVISION:
                            res = first / second;
                            result.setText(Integer.toString(res));
                            break;
                        case MULTIPLY:
                            res = first * second;
                            result.setText(Integer.toString(res));
                            break;
                    }
                }
                catch (Exception err) {
                    result.setText("Error");
                }
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel panel = new JPanel();

        frame.add(panel);

        placeComponents(panel);

        currentMode = action.NONE;

        frame.setVisible(true);
    }
}
