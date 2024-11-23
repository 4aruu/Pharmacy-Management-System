package PharmacyManagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DoctorsGUI extends JFrame {
    private Doctors doctors;
    private JTable doctorsTable;

    public DoctorsGUI(Connection connection) {
        super("Doctors Management");

        doctors = new Doctors(connection);

        // Create components
        JButton viewButton = new JButton("View Doctors");
         
        JTextArea outputArea = new JTextArea(20, 40);
        outputArea.setEditable(false);

        // Create DefaultTableModel for JTable
        DefaultTableModel tableModel = new DefaultTableModel();
        doctorsTable = new JTable(tableModel);

        // Create panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(viewButton);
         
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(doctorsTable), BorderLayout.CENTER);

        // Set background image
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/icons/login.jpg")));
        background.setLayout(new BorderLayout());
        background.add(mainPanel, BorderLayout.CENTER); // Add the mainPanel to the background

        setContentPane(background); // Use setContentPane to set the background as the content pane

        // Add action listeners
        viewButton.addActionListener(e -> {
            try {
                DefaultTableModel model = doctors.getAllDoctors();
                doctorsTable.setModel(model); // Set model to JTable
            } catch (SQLException ex) {
                ex.printStackTrace();
                outputArea.setText("Error fetching doctors.");
            }
        });

        // Set frame properties
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 350);
        setLocationRelativeTo(null); // Center the frame on screen
        setVisible(true);
    }

    public static void main(String[] args) {
        // Valid database connection
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "adithaaron");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new DoctorsGUI(connection);
    }
}
