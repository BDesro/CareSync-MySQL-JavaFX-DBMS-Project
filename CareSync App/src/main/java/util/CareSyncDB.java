package util;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class CareSyncDB
{
    private static final String url = "jdbc:mysql://localhost:3306/caresync";
    private static final String user = "app_user";
    private static final String pass = "safePassword!";

    public static Connection getConnection()
    {
        try
        {
            return DriverManager.getConnection(url, user, pass);
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static Staff getStaffByEmail(String email)
    {
        Staff staff = null;

        String query = "SELECT * " +
                       "FROM staff s JOIN staff_auth sa " +
                       "ON s.staff_id = sa.staff_id " +
                       "WHERE sa.email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if(rs.next())
            {
                int staffID = rs.getInt("staff_id");
                String fName = rs.getString("first_name");
                String lName = rs.getString("last_name");
                String role = rs.getString("staff_role");
                int clinicID = rs.getInt("clinic_id");
                String hashedPassword = rs.getString("hashed_password");

                staff = new Staff(staffID, fName, lName, role, clinicID, email, hashedPassword);
            }
            rs.close();
            return staff;
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static ObservableList<Staff> pullDoctorsFromDB()
    {
        ObservableList<Staff> dbDoctors = FXCollections.observableArrayList();

        String query = "SELECT * FROM staff s JOIN staff_auth sa ON s.staff_id = sa.staff_id " +
                       "WHERE staff_role = 'doctor' AND clinic_id = ? ORDER BY last_name";

        try(Connection connection = CareSyncDB.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setInt(1, AppGlobals.currentClinic.getClinicID());
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt("staff_id");
                String fName = rs.getString("first_name");
                String lName = rs.getString("last_name");
                String role = rs.getString("staff_role");
                int clinic_id = rs.getInt("clinic_id");
                String email = rs.getString("email");
                String hashedPassword = rs.getString("hashed_password");

                dbDoctors.add(new Staff(id, fName, lName, role, clinic_id, email, hashedPassword));
            }
            rs.close();
            return dbDoctors;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return FXCollections.observableArrayList();
        }
    }

    public static Patient getPatientByID(int id)
    {
        Patient patient = null;

        String query = "SELECT * " +
                       "FROM patients p " +
                       "WHERE p.patient_id = ?";
        try(Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next())
            {
                String fName = rs.getString("first_name");
                char middle_init = rs.getString("middle_init").charAt(0);
                String lName = rs.getString("last_name");
                LocalDate dob = LocalDate.parse(rs.getString("date_of_birth"));
                char gender = rs.getString("gender").charAt(0);
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                int contact_id = rs.getInt("contact_id");

                patient = new Patient(id, fName, middle_init, lName, dob, gender, phone, email, contact_id);
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
        return patient;
    }

    public static ObservableList<Patient> pullPatientsFromDB()
    {
        ObservableList<Patient> dbPatients = FXCollections.observableArrayList();

        String query = "SELECT * FROM patients ORDER BY last_name";

        try(Connection connection = CareSyncDB.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query))
        {
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt("patient_id");
                String fName = rs.getString("first_name");
                char middle_init = rs.getString("middle_init").charAt(0);
                String lName = rs.getString("last_name");
                LocalDate dob = LocalDate.parse(rs.getString("date_of_birth"));
                char gender = rs.getString("gender").charAt(0);
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                int contact_id = rs.getInt("contact_id");

                dbPatients.add(new Patient(id, fName, middle_init, lName, dob, gender, phone, email, contact_id));
            }
            rs.close();
            return dbPatients;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Clinic getClinicByID(int id)
    {
        Clinic clinic = null;

        String query = "SELECT * " +
                "FROM clinics c JOIN clinic_addresses ca ON c.address_id = ca.address_id " +
                "WHERE c.clinic_id = ?";
        try(Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next())
            {
                String clinicName = rs.getString("clinic_name");
                String phone = rs.getString("phone");
                String address = rs.getString("street_address") + " " + rs.getString("city") + ", " + rs.getString("state");

                clinic = new Clinic(id, clinicName, phone, address);
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
        return clinic;
    }

    public static ObservableList<Room> pullRoomsFromDB()
    {
        ObservableList<Room> dbRooms = FXCollections.observableArrayList();

        String query = "SELECT * FROM rooms";

        try(Connection connection = CareSyncDB.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query))
        {
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt("room_id");
                String roomType = rs.getString("room_type");

                dbRooms.add(new Room(id, roomType));
            }
            rs.close();
            return dbRooms;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Visit getVisitByID(int id)
    {
        Visit visit = null;

        String query = "SELECT * " +
                "FROM recent_visits rv " +
                "WHERE rv.record_id = ?";
        try(Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next())
            {
                int patientID = rs.getInt("patient_id");
                int clinicID = rs.getInt("clinic_id");
                int roomID = rs.getInt("room_id");
                int staffID = rs.getInt("staff_id");
                String reason = rs.getString("reason_for_visit");
                LocalDate date = rs.getTimestamp("visit_date").toLocalDateTime().toLocalDate();
                String patientFName = rs.getString("patient_first_name");
                char patientMInit = rs.getString("patient_middle_init").charAt(0);
                String patientLName = rs.getString("patient_last_name");
                String clinicName = rs.getString("clinic_name");
                String roomType = rs.getString("room_type");
                String staffRole = rs.getString("staff_role");
                String staffFName = rs.getString("staff_first_name");
                String staffLName = rs.getString("staff_last_name");
                String symptoms = rs.getString("symptoms");
                String diagnosis = rs.getString("diagnosis");
                String treatments = rs.getString("treatments");
                String prescriptions = rs.getString("prescriptions");
                boolean completed = rs.getBoolean("is_complete");

                visit = new Visit(id, patientID, clinicID, roomID, staffID, reason, date, patientFName, patientMInit, patientLName,
                                  clinicName, roomType, staffRole, staffFName, staffLName, symptoms, diagnosis, treatments, prescriptions, completed);
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
        return visit;
    }

    public static ObservableList<Visit> pullVisitsFromDB()
    {
        ObservableList<Visit> dbVisits = FXCollections.observableArrayList();

        String query = "SELECT * FROM recent_visits WHERE clinic_id = ? ";
        if(AppGlobals.activeUser.getStaffRole().equals("doctor"))
            query += "&& staff_id = ? ";
        query += " ORDER BY visit_date DESC";

        try(Connection conn = CareSyncDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, AppGlobals.activeUser.getClinicID());
            if(AppGlobals.activeUser.getStaffRole().equals("doctor"))
                stmt.setInt(2, AppGlobals.activeUser.getStaffID());
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                int visitID = rs.getInt("record_id");
                int patientID = rs.getInt("patient_id");
                int clinicID = rs.getInt("clinic_id");
                int roomID = rs.getInt("room_id");
                int staffID = rs.getInt("staff_id");
                String reason = rs.getString("reason_for_visit");
                Timestamp timestamp = rs.getTimestamp("visit_date");
                LocalDate date = timestamp.toLocalDateTime().toLocalDate();
                String pFName = rs.getString("patient_first_name");
                char pMInit = rs.getString("patient_middle_init").charAt(0);
                String pLName = rs.getString("patient_last_name");
                String clinicName = rs.getString("clinic_name");
                String roomType = rs.getString("room_type");
                String staffRole = rs.getString("staff_role");
                String staffFirstName = rs.getString("staff_first_name");
                String staffLastName = rs.getString("staff_last_name");
                String symptoms = rs.getString("symptoms");
                String diagnosis = rs.getString("diagnosis");
                String treatments = rs.getString("treatments");
                String prescriptions = rs.getString("prescriptions");
                boolean complete = rs.getBoolean("is_complete");

                dbVisits.add(new Visit(visitID, patientID, clinicID, roomID, staffID, reason, date,
                        pFName, pMInit, pLName, clinicName, roomType, staffRole, staffFirstName,
                        staffLastName, symptoms, diagnosis, treatments, prescriptions, complete));
            }
            rs.close();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        return dbVisits;
    }
}
