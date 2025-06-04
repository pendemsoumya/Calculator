import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    // Updated Colors
    Color lightBlue = new Color(173, 216, 230);
    Color skyBlue = new Color(100, 149, 237);
    Color darkNavy = new Color(25, 25, 112);
    Color displayBackground = Color.white;
    Color displayText = Color.black;

    String[] buttonValues = {
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String A = "0";
    String operator = null;
    String B = null;

    Calculator() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(displayBackground);
        displayLabel.setForeground(displayText);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(darkNavy);
        frame.add(buttonsPanel);

        for (String buttonValue : buttonValues) {
            JButton button = new JButton();
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(darkNavy));

            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(lightBlue);
                button.setForeground(Color.BLACK);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(skyBlue);
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(darkNavy);
                button.setForeground(Color.WHITE);
            }

            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String btnText = button.getText();
                    if (Arrays.asList(rightSymbols).contains(btnText)) {
                        if (btnText.equals("=")) {
                            if (A != null) {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);
                                switch (operator) {
                                    case "+": displayLabel.setText(removeZeroDecimal(numA + numB)); break;
                                    case "-": displayLabel.setText(removeZeroDecimal(numA - numB)); break;
                                    case "×": displayLabel.setText(removeZeroDecimal(numA * numB)); break;
                                    case "÷": displayLabel.setText(removeZeroDecimal(numA / numB)); break;
                                }
                                clearAll();
                            }
                        } else {
                            if (operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = btnText;
                        }
                    } else if (Arrays.asList(topSymbols).contains(btnText)) {
                        switch (btnText) {
                            case "AC":
                                clearAll();
                                displayLabel.setText("0");
                                break;
                            case "+/-":
                                double val = Double.parseDouble(displayLabel.getText());
                                displayLabel.setText(removeZeroDecimal(-val));
                                break;
                            case "%":
                                double percent = Double.parseDouble(displayLabel.getText());
                                displayLabel.setText(removeZeroDecimal(percent / 100));
                                break;
                        }
                    } else {
                        if (btnText.equals(".")) {
                            if (!displayLabel.getText().contains(".")) {
                                displayLabel.setText(displayLabel.getText() + ".");
                            }
                        } else {
                            if (displayLabel.getText().equals("0")) {
                                displayLabel.setText(btnText);
                            } else {
                                displayLabel.setText(displayLabel.getText() + btnText);
                            }
                        }
                    }
                }
            });
        }

        frame.setVisible(true);
    }

    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double number) {
        return (number % 1 == 0) ? Integer.toString((int) number) : Double.toString(number);
    }

    public static void main(String[] args) {
        new Calculator(); // Run the app
    }
}
