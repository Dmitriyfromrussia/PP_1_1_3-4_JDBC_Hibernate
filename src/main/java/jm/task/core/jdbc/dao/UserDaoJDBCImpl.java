package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Connection connection = openConnection();
             Statement statement = connection.createStatement();) {
            statement.execute("CREATE TABLE IF NOT EXISTS users (id int auto_increment primary key, " +
                    "name varchar(25) null, lastName varchar(25) null, age smallint null) engine InnoDB");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = openConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        String sqlRequest = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void removeUserById(long id) {
        String sqlRequest = "DELETE FROM users WHERE id = ?";
        try (Connection connection = openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
            preparedStatement.setLong(1, id);
            //preparedStatement.execute(); //при наличии / отсутвиии этой строки компиляция кода не меняется
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsersList = new ArrayList<>();
        String sqlRequest = "SELECT id, name, lastname, age FROM users"; // либо SELECT * FROM users
        try (Connection connection = openConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                allUsersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace(); //либо throw new RuntimeException(e), прошу ментора сообщить как правильнее
        }
        return allUsersList;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = openConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
