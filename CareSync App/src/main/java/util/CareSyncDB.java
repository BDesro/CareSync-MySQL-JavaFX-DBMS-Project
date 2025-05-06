package util;

import Model.Staff;

import java.sql.*;

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
}
