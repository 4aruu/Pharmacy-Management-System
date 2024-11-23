package PharmacyManagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Viewpatients extends JFrame {
    private Connection connection;
    private JTable table;

    public Viewpatients() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "adithaaron");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setTitle("View Patients");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
       

        // Set the background image as the content pane
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/icons/login.jpg")));
        setContentPane(background);
        background.setLayout(new BorderLayout()); // Set layout for the background

        // Create a transparent panel for components
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false); // Make the panel transparent
        background.add(contentPanel, BorderLayout.CENTER); // Add content panel to background

        // Create buttons
        JButton addButton = new JButton("Add Patient");
        
        JPanel buttonPanel = new JPanel(new FlowLayout()); // Panel for buttons
        buttonPanel.setOpaque(false); // Make the button panel transparent
        buttonPanel.add(addButton);
        
        contentPanel.add(buttonPanel, BorderLayout.NORTH); // Add button panel to content panel

        // Create a table with a default table model
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER); // Add table to content panel

        // Fetch data from database and populate the table
        fetchDataAndPopulateTable();
        setVisible(true);

        // Add action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                dispose(); // Close the Viewpatients window
                new PatientGUI(connection).setVisible(true); // Open the PatientGUI window
            }
        });
        };
    

    private void fetchDataAndPopulateTable() {
        String query = "SELECT * FROM patients";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                model.addRow(rowData);
            }

            table.setModel(model);

        } catch (SQLException e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // valid database connection
        new Viewpatients();
    }
}
