package util;

import Model.Staff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        String query = "SELECT * " +
                       "FROM staff s JOIN staff_auth sa" +
                       "ON s.staff_id = sa.staff_id" +
                       "WHERE sa.email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            return null;
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
