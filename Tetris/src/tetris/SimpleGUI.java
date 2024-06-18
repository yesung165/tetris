package tetris;

import javax.swing.*; // Importing Swing package
import java.awt.*; // Importing AWT package for layout managers
import java.awt.event.*; // Importing AWT package for event handling

public class SimpleGUI {
    public static void main(String[] args) {
        // Create a new frame
        JFrame frame = new JFrame("Simple GUI Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200); // Set the frame size

        // Create a panel to hold the components
        JPanel panel = new JPanel(); 
        frame.add(panel); 
        placeComponents(panel);

        // Set the frame visibility to true
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null); // Set the layout to null to manually position the components

        // Create a label
        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        // Create a text field
        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        // Create a button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        // Add an action listener to the button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                JOptionPane.showMessageDialog(panel, "Hello, " + username);
            }
        });
    }
}
