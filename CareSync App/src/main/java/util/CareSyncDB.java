package util;

import java.sql.Connection;
import java.sql.DriverManager;
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
}
