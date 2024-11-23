# Pharmacy-Management-System
 
# Doctor Appointment Booking System  

This project, developed using **Java** in the **Eclipse IDE**, is an **admin-use-only system** designed to streamline the scheduling and management of doctor appointments. All doctor and patient data is managed directly in the **MySQL database**, providing secure and centralized record-keeping.  

## Features  

- **Appointment Management**  
  - Book appointments based on doctor availability.  
  - Update or delete existing appointments.  
  - View all appointment records.  

- **Doctor and Patient Data Management**  
  - Doctors and patients are added or updated directly via the MySQL database.  
  - Only admin users have access to the system.  

- **Database Integration**  
  - Data is securely stored and managed in a MySQL database using **MySQL Connector for Java**.  

## Technologies Used  

- **Programming Language**: Java  
- **IDE**: Eclipse  
- **Database**: MySQL  
- **Connector**: MySQL Connector/J  

---

## Installation and Setup  

1. **Clone the Repository**  
   ```bash  
   git clone https://github.com/your-username/doctor-appointment-system.git  
   ```  

2. **Import into Eclipse**  
   - Open Eclipse.  
   - Go to `File > Import > Existing Projects into Workspace`.  
   - Select the project directory and click `Finish`.  

3. **Set Up MySQL**  
   - Install MySQL Server and create a database:  
     ```sql  
     CREATE DATABASE doctor_appointment_system;  
     ```  
   - Create tables for doctors and appointments using the provided `schema.sql` file:  
     ```bash  
     mysql -u username -p doctor_appointment_system < schema.sql  
     ```  

4. **Configure Database Connection**  
   - Update the `dbConfig` class with your MySQL credentials:  
     ```java  
     String url = "jdbc:mysql://localhost:3306/doctor_appointment_system";  
     String user = "your_username";  
     String password = "your_password";  
     ```  

5. **Install MySQL Connector**  
   - Add the MySQL Connector/J JAR file to your Eclipse project:  
     - Right-click the project > `Build Path > Add External Archives`.  
     - Select the MySQL Connector JAR file.  

6. **Run the Project**  
   - Open the main class in the `src` folder.  
   - Right-click and select `Run As > Java Application`.  

---

## Usage  

1. **Manage Appointments**  
   - Use the system to:  
     - Book appointments with doctors based on availability.  
     - View or delete existing appointments.  

2. **Add or Update Data in MySQL**  
   - Add doctors and patients directly via the MySQL interface:  
     ```sql  
     INSERT INTO doctors (id, name, specialization, availability) VALUES (1, 'Dr. John Doe', 'Cardiology', true);  
     INSERT INTO patients (id, name, age, contact) VALUES (1, 'Jane Doe', 30, '123-456-7890');  
     ```  
   - View data using queries like:  
     ```sql  
     SELECT * FROM doctors;  
     SELECT * FROM patients;  
     ```  

---

## Future Enhancements  

- **Admin Authentication**: Add login functionality for secure admin access.  
- **Improved Appointment Search**: Enable searching by doctor name or patient details.  
- **Export Reports**: Generate and export appointment and doctor schedules as PDFs.  

---

## Contributing  

Contributions are welcome! Fork this repository and create a pull request with detailed information about your changes.  

---

## License  

This project is licensed under the [MIT License](LICENSE).  

---

Let me know if there’s anything else to fine-tune! 😊
