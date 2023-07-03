import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorApp extends JFrame {
    private JTextField display;
    private StringBuilder input;

    public CalculatorApp() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        input = new StringBuilder();
        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);

        JPanel buttonPanel = createButtonPanel();

        add(display, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttonLabels = { "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", "=", "+" };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        return panel;
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "=":
                    try {
                        String result = evaluateExpression(input.toString());
                        display.setText(result);
                        input = new StringBuilder(result);
                    } catch (Exception ex) {
                        display.setText("Error");
                    }
                    break;
                default:
                    input.append(command);
                    display.setText(input.toString());
            }
        }
    }

    private String evaluateExpression(String expression) {
        try {
            return String.valueOf(new ScriptEngineManager().getEngineByName("JavaScript").eval(expression));
        } catch (Exception e) {
            return "Error";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CalculatorApp calculator = new CalculatorApp();
                calculator.setVisible(true);
            }
        });
    }
}
