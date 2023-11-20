package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE users (id int AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(45) NOT NULL,last_name VARCHAR(45),age TINYINT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("таблица не создана: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("таблица не удалена: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement pstmt = connection
                .prepareStatement("insert into users (name, last_name, age) VALUES(?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);
            pstmt.executeUpdate();
//            connection.prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            System.out.println("user не создан: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        String query = "DELETE from users where id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("user не удалена по id: " + id + "\n" + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();

        try (ResultSet resultSet = connection.createStatement().executeQuery(query)) {
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


    @Override
    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE users";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("таблица не очищена: " + e.getMessage());
        }
    }
}