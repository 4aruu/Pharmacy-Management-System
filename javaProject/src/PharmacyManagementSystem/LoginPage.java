package PharmacyManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame implements ActionListener {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;

    public LoginPage() {
        setTitle("Login Page");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/icons/login.jpg")));
        setContentPane(background);
        setLayout(new BorderLayout()); // Use BorderLayout for simplicity

        JPanel panel = new JPanel(null);
        panel.setOpaque(false); // Make panel transparent

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        loginButton.addActionListener(this);

        usernameLabel.setBounds(50, 50, 100, 30);
        usernameField.setBounds(150, 50, 200, 30);
        passwordLabel.setBounds(50, 100, 100, 30);
        passwordField.setBounds(150, 100, 200, 30);
        loginButton.setBounds(200, 150, 100, 30);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);           // Add panel to the center of the frame

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Check if username and password are correct
            if (username.equals("admin") && password.equals("password")) {
                dispose(); // Close the current LoginPage window
                new PharmacyManagementGUI(); // Open the PharmacyManagementGUI window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
