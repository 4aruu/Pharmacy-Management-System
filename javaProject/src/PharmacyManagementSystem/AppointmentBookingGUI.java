package PharmacyManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentBookingGUI extends JFrame {
    private JTextField patientIdField;
    private JTextField doctorIdField;
    private JTextField appointmentDateField;
    private JButton bookButton;
    private JTextArea statusTextArea;
    private Connection connection;

    public AppointmentBookingGUI(Connection connection) {
        this.connection = connection;

        setTitle("Appointment Booking");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a JLabel for the background image
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/icons/login.jpg")));
        setContentPane(background);
        background.setLayout(new BorderLayout()); // Set layout for background

        initComponents();
        addComponents();
        addActionListeners();
        setVisible(true);
        setResizable(false);
    }

    private void initComponents() {
        patientIdField = new JTextField(10);
        doctorIdField = new JTextField(10);
        appointmentDateField = new JTextField(10);
        bookButton = new JButton("Book Appointment");
        statusTextArea = new JTextArea(5, 20);
        statusTextArea.setEditable(false);

        // Set the background for text area to make it transparent
        statusTextArea.setOpaque(false);
        statusTextArea.setBackground(new Color(0, 0, 0, 0)); // Fully transparent
    }

    private void addComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.setOpaque(false); // Make the panel transparent
        panel.add(new JLabel("Patient ID:"));
        panel.add(patientIdField);
        panel.add(new JLabel("Doctor ID:"));
        panel.add(doctorIdField);
        panel.add(new JLabel("Appointment Date (YYYY-MM-DD):"));
        panel.add(appointmentDateField);
        panel.add(new JLabel("Status:"));
        panel.add(statusTextArea);

        JPanel buttonPanel = new JPanel();        // Create a new JPanel for buttons
        buttonPanel.setOpaque(false);             // Make the button panel transparent
        buttonPanel.add(bookButton);           // Add the button to the button panel

        // Create a main panel for organizing components
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);                     // Make the main panel transparent
        mainPanel.add(panel, BorderLayout.CENTER);                    // Add component panel to main panel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);                // Add button panel to main panel

        getContentPane().add(mainPanel, BorderLayout.CENTER);           // Add main panel to content pane
    }


    private void addActionListeners() {
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the current date
                    java.util.Date currentDate = new java.util.Date();
                    java.sql.Date sqlCurrentDate = new java.sql.Date(currentDate.getTime());

                    int patientId = Integer.parseInt(patientIdField.getText());
                    int doctorId = Integer.parseInt(doctorIdField.getText());
                    String appointmentDateString = appointmentDateField.getText();

                    // Parse the entered date string into a java.sql.Date object
                    java.sql.Date appointmentDate = java.sql.Date.valueOf(appointmentDateString);

                    if (appointmentDate.before(sqlCurrentDate)) {
                        statusTextArea.setFont(new Font("Arial", Font.BOLD, 14));
                        statusTextArea.setText("Please enter a date equal to or after today.");
                        return;
                    }

                    // Check for repeated appointments
                    String checkQuery = "SELECT * FROM appointments WHERE patient_id = ? AND doctor_id = ? AND appointment_date = ?";
                    PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
                    checkStatement.setInt(1, patientId);
                    checkStatement.setInt(2, doctorId);
                    checkStatement.setDate(3, appointmentDate);
                    ResultSet resultSet = checkStatement.executeQuery();

                    if (resultSet.next()) {
                        statusTextArea.setFont(new Font("Arial", Font.BOLD, 14));
                        statusTextArea.setText("Appointment already exists for this patient and doctor on the selected date.");
                    } else {
                        bookAppointment(patientId, doctorId, appointmentDateString);
                    }
                } catch (NumberFormatException ex) {
                    statusTextArea.setFont(new Font("Arial", Font.BOLD, 14));
                    statusTextArea.setText("Invalid input format for ID fields.");
                } catch (SQLException ex) {
                    statusTextArea.setText("Database error: " + ex.getMessage());
                }
            }
        });
    }


    private void bookAppointment(int patientId, int doctorId, String appointmentDate) {
        String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
            preparedStatement.setInt(1, patientId);
            preparedStatement.setInt(2, doctorId);
            preparedStatement.setString(3, appointmentDate);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
            	statusTextArea.setFont(new Font("Arial", Font.BOLD, 16));
                statusTextArea.setText("APPOINTMENT BOOKED");
            } else {
            	statusTextArea.setFont(new Font("Arial", Font.BOLD, 14));
                statusTextArea.setText("FAILED TO BOOK APPOINTMENT");
            }
        } catch (SQLException e) {
            statusTextArea.setText("Failed to book appointment: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            // Create a connection object
            Connection connection = YourDatabaseConnector.getConnection();

            // Create and display the GUI
            new AppointmentBookingGUI(connection);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }
}
