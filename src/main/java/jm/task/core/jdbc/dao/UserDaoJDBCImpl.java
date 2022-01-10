package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;


import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

        try (Connection connection = Util.getMySQLConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("create table if not exists user (ID bigint NOT NULL AUTO_INCREMENT, " +
                    "LASTNAME varchar(40) NOT NULL, " +
                    "NAME varchar(40) NOT NULL, " +
                    "AGE tinyint, PRIMARY KEY (ID));");
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try (Connection connection = Util.getMySQLConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("drop table if exists user;");
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        try (Connection connection = Util.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Insert into user (NAME, LASTNAME, AGE)" +
                     "values (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try (Connection connection = Util.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from user where id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from user")) {
            ResultSet resultSet = preparedStatement.executeQuery("select * from user");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                users.add(user);
            }
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        return users;
    }

    public void cleanUsersTable() {

        try (Connection connection = Util.getMySQLConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("truncate table user");
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
