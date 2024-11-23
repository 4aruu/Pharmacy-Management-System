package PharmacyManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Doctors {
	private Connection connection;
	
	public Doctors(Connection connection)
	{
		this.connection=connection;

	}
	public DefaultTableModel getAllDoctors() throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Specialization");

        String query = "SELECT * FROM doctors";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                model.addRow(new Object[]{id, name, specialization});
            }
        }

        return model;
    }

	 
	
	public void viewDoctors(JTextArea outputArea)
	{
		String query="select * from doctors";
		try 
		{
			PreparedStatement preparedstatement=connection.prepareStatement(query);
			ResultSet resultset= preparedstatement.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("+--------+--------------+------+-----+-----------+");
			System.out.println("| Doctor ID  | Name         |   Specialization   |");
			System.out.println("+--------+--------------+------+-----+-----------+");
			while(resultset.next())
			{
				int id=resultset.getInt("id");
				String name=resultset.getString("name");
				String specialization=resultset.getString("specialization");
				System.out.printf("| %-10s | %-12s | %-18s |\n",id,name,specialization);
				System.out.println("+--------+--------------+------+-----+-----------+");
			}
		}
			
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorById(int id) throws SQLException {
        String query = "SELECT * FROM doctors WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
			
		}
		
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
		
	}

}