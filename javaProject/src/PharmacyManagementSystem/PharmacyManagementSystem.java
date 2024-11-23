package PharmacyManagementSystem;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;

import javax.swing.JTextArea;


public class PharmacyManagementSystem 
{
	private static final String url ="jdbc:mysql://localhost:3306/hospital";
	private static final String username ="root";
	private static final String password ="adithaaron";
	private static final JTextArea JTextArea = null;
	
	
	public static void main(String[] args)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		Scanner scanner= new Scanner(System.in);
		
		try {
			Connection connection = DriverManager.getConnection(url,username,password);
			Patient patient= new Patient(connection,scanner);
			Doctors doctor=new Doctors(connection);
			while(true)
			{
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1.  Add Patients");
				System.out.println("2.   View Patients");
				System.out.println("3.  View Doctors");
				System.out.println("4.  Book Appointments");
				System.out.println("5.  Exit ");
				System.out.println("Enter Your Choice");
				int choice =scanner.nextInt();
				
				switch(choice)
				{
				case 1:
				
					patient.addPatient();
					System.out.println();
					break;
				
				case 2:
				
					patient.viewPatients();
					System.out.println();
					break;
				
				case 3:
				
					doctor.viewDoctors(JTextArea);
					System.out.println();
					break;
					
				case 4:
					bookAppointment(patient,doctor,connection,scanner);
					System.out.println();
					break;
					
				case 5:
					System.out.println("THANK YOU FOR USING PHARMACY MANAGEMENT SYSTEM");
					return ; 
				default:
					System.out.println("wrong choice");
					break;
					
				}
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public static void bookAppointment(Patient patient,Doctors doctor,Connection connection,Scanner scanner) throws SQLException
	{
		System.out.println("Enter Patient ID: ");
		int patientid=scanner.nextInt();
		System.out.println("Enter Doctor Id: ");
		int doctorid=scanner.nextInt();
		System.out.println("Enter Appointment date(YYYY-MM-DD): ");
		String appointmentDate=scanner.next();
		if(patient.getPatientbyId(patientid) && doctor.getDoctorById(doctorid))
		{
			if(checkDoctorAvailability(doctorid,appointmentDate,connection)) {
				String appointmentQuery="insert into appointments(patient_id,doctor_id,appointment_date)values(?,?,?)";
				try {
					PreparedStatement preparedstatement=connection.prepareStatement(appointmentQuery);
					preparedstatement.setInt(1,patientid);
					preparedstatement.setInt(2,doctorid);
					preparedstatement.setString(3,appointmentDate);
					int rowsAffected=preparedstatement.executeUpdate();
					if (rowsAffected>0)
					{
						System.out.println("APPOINTMENT BOOKED");
						
					}else
					{
						System.out.println("FAILED TO BOOK APPOINTMENT");
					}
					
				}catch(SQLException e)
				{
					e.printStackTrace();
				}
				
			}else {
				System.out.println("Doctors not available on this day");
			}
		}else {
			System.out.println("Either Patient or Doctor exist ");
		}
		
	}
	public static boolean checkDoctorAvailability(int doctorId,String appointmentDate,Connection connection)
	{
		String query="select count(*) from appointments where doctor_id=? and appointment_date=?";
		try {
			PreparedStatement preparedstatement=connection.prepareStatement(query);
			preparedstatement.setInt(1,doctorId);
			preparedstatement.setString(2,appointmentDate);
			ResultSet resultset=preparedstatement.executeQuery();
			if (resultset.next())
			{
				int count=resultset.getInt(1);
				if(count==0)
				{
					return true;
				}
				
				else
					return false;
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

}