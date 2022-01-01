package jm.task.core.jdbc.util;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {

        String userName = "root";
        String password = "59333";

        Class.forName("com.mysql.cj.jdbc.Driver");
        String connectionURL = "jdbc:mysql://localhost:3306/test?useLegacyDatetimeCode=false&serverTimezone=UTC";

        return DriverManager.getConnection(connectionURL, userName, password);
    }
}
