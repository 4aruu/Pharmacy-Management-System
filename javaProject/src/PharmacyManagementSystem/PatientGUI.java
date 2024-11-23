package PharmacyManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PatientGUI extends JFrame {
    private Connection connection;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField genderField;
    public PatientGUI(Connection connection) {
        this.connection = connection;
        setTitle("Patient Management");
        setSize(600, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Set the layout to null for absolute positioning
        setLayout(null);
        setResizable(false);

        // Background image
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/icons/login.jpg")));
        background.setBounds(0, 0, 600, 350);
        add(background);

        // Panel for adding patient details
        JPanel addPatientPanel = new JPanel(new GridLayout(4, 2));
        addPatientPanel.setBounds(20, 20, 300, 120);
        addPatientPanel.setOpaque(false); // Make the panel transparent
        background.add(addPatientPanel);

        // Add components to the panel
        addPatientPanel.add(new JLabel("Name:"));
        nameField = new JTextField(30);
        addPatientPanel.add(nameField);
        addPatientPanel.add(new JLabel("Age:"));
        ageField = new JTextField(5);
        addPatientPanel.add(ageField);
        addPatientPanel.add(new JLabel("Gender:"));
        genderField = new JTextField(10);
        addPatientPanel.add(genderField);
        JButton addPatientButton = new JButton("Add Patient");
        addPatientPanel.add(addPatientButton);

        // Set bounds for the button
        addPatientButton.setBounds(40, 150, 120, 30);
        background.add(addPatientButton); 
        addPatientButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                addPatient();
            }
        });
    }

    private void addPatient() {
        String name = nameField.getText().trim();
        String ageStr = ageField.getText().trim();
        String gender = genderField.getText().trim();

        if (name.isEmpty() || ageStr.isEmpty() || gender.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);
            String query = "INSERT INTO patients(name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Patient added successfully.");
                nameField.setText("");
                ageField.setText("");
                genderField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add patient.");
            }
        } catch (NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Invalid age format or database error.");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "adithaaron");
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new PatientGUI(connection).setVisible(true);
                }
            });
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
