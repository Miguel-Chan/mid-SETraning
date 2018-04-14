import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class Calculator extends JFrame {
    private enum action {NONE, PLUS, MINUS, MULTIPLY, DIVISION}

    private static action currentMode;

    private static JTextField createTextBox(int x, int y) {
        JTextField box = new JTextField(20);
        box.setBounds(x, y, 70, 70);
        box.setHorizontalAlignment(JTextField.CENTER);
        box.setEditable(false);
        return box;
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        NumberFormat digFormat = NumberFormat.getIntegerInstance();

        NumberFormatter numberFormatter = new NumberFormatter(digFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);


        JFormattedTextField firstDigit = new JFormattedTextField(numberFormatter);
        firstDigit.setBounds(5, 5, 70, 70);
        firstDigit.setHorizontalAlignment(JTextField.CENTER);
        firstDigit.setFont(new Font("Fira Code", Font.PLAIN, 12));
        panel.add(firstDigit);

        JTextField operator = createTextBox(80, 5);
        panel.add(operator);

        JFormattedTextField secondDigit = new JFormattedTextField(numberFormatter);
        secondDigit.setBounds(155, 5, 70, 70);
        secondDigit.setHorizontalAlignment(JTextField.CENTER);
        secondDigit.setFont(new Font("Fira Code", Font.PLAIN, 12));
        panel.add(secondDigit);

        JTextField equalSign = createTextBox(230, 5);
        equalSign.setText("=");
        panel.add(equalSign);

        JTextField result = createTextBox(305, 5);
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
                    int first, second;
                    first = Integer.parseInt(firstDigit.getText().replaceAll(",", ""));
                    second = Integer.parseInt(secondDigit.getText().replaceAll(",", ""));
                    if (currentMode != action.NONE) {
                      result.setText(Integer.toString(calculate(first, second, currentMode)));
                    }
                } catch (Exception err) {
                    result.setText("Error");
                }
            }
        });
    }

    private static int calculate(int first, int second, action mode) {
        int res = 0;
        switch (mode) {
            case PLUS:
                res = first + second;
                break;
            case MINUS:
                res = first - second;
                break;
            case DIVISION:
                res = first / second;
                break;
            case MULTIPLY:
                res = first * second;
                break;
        }
        return res;
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
