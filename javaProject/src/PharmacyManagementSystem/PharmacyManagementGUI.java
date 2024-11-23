package PharmacyManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PharmacyManagementGUI extends JFrame implements ActionListener {
    private JButton addPatientButton;
    private JButton viewPatientsButton;
    private JButton viewDoctorsButton;
    private JButton bookAppointmentButton;
    private  Connection connection;

    public PharmacyManagementGUI()
    {
    	 try {
             connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "adithaaron");
         } catch (SQLException e) {
             JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
             e.printStackTrace();
         }

        setTitle("Pharmacy Management System");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/icons/login.jpg")));
        JLabel image = new JLabel();
 
        
        
        

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 3));

        addPatientButton = new JButton("Add Patients");
        viewPatientsButton = new JButton("View Patients");
        viewDoctorsButton = new JButton("View Doctors");
        bookAppointmentButton = new JButton("Book Appointment");

        addPatientButton.addActionListener(this);
        viewPatientsButton.addActionListener(this);
        viewDoctorsButton.addActionListener(this);
        bookAppointmentButton.addActionListener(this);
        
        panel.add(image);

        panel.add(addPatientButton);
        panel.add(viewPatientsButton);
        panel.add(viewDoctorsButton);
        panel.add(bookAppointmentButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPatientButton) {
            new PatientGUI(this.connection).setVisible(true);             // Logic for adding patients
            
            
        } else if (e.getSource() == viewPatientsButton) {                // Logic for viewing patients
        	new Viewpatients();
        	
          } else if (e.getSource() == viewDoctorsButton) { 				// Logic for viewing doctors
           new DoctorsGUI(connection);
           
           
         } else if (e.getSource() == bookAppointmentButton) {             // Logic for booking appointments
        	 new AppointmentBookingGUI(connection);
        	 
         }
    }


    public static void main(String[] args) {
        
       
        new PharmacyManagementGUI();
    }
    }
