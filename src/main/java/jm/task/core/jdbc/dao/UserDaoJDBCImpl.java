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

        String createTable =
                "create table if not exists user (ID bigint NOT NULL AUTO_INCREMENT, " +
                        "NAME varchar(40) NOT NULL, " +
                        "LASTNAME varchar(40) NOT NULL, " +
                        "AGE tinyint, PRIMARY KEY (ID));";

        try (Connection connection = Util.getMySQLConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTable);
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void dropUsersTable() {

        String dropTable = "drop table if exists user;";

        try (Connection connection = Util.getMySQLConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(dropTable);
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        String saveUser =
                "Insert into user (NAME, LASTNAME, AGE)" +
                        "values (?, ?, ?)";

        try (Connection connection = Util.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        String removeUserById =
                "delete from user where id=?";

        try (Connection connection = Util.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeUserById)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        String getAllUsers =
                "select * from user";

        try (Connection connection = Util.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllUsers)) {
            ResultSet resultSet = preparedStatement.executeQuery(getAllUsers);
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

        String clearUserTable =
                "truncate table user";

        try (Connection connection = Util.getMySQLConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(clearUserTable);
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
