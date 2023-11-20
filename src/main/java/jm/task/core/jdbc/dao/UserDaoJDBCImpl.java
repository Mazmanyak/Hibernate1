package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Connection connection;

    static {
        connection = Util.getConnection();
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE users (\n" +
                "id int AUTO_INCREMENT PRIMARY KEY,\n" +
                "name VARCHAR(45) NOT NULL,\n" +
                "last_name VARCHAR(45),\n" +
                "age TINYINT\n" +
                " )";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("таблица не создана: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("таблица не удалена: " + e.getMessage());
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        String query = "insert into users (name, last_name, age) value('" + name + "', '" + lastName + "', " + age + ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("user не создан: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE from users where id = " + id;
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("user не удалена по id: " + id + "\n" + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();

        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("возвращение всех user'ов не произошло\n" + e.getMessage());
        }

        return userList;
    }


    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE users";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("таблица не очищена: " + e.getMessage());
        }
    }
}