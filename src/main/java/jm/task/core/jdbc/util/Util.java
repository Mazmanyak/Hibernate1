package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
//    private static final String DRIVER = "org.mysql.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/tz1.1.2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("нет подключения к БД");;
        }
            return connection;
    }

}

























