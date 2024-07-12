package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users (id int auto_increment primary key, " + "name varchar(25) null, lastName varchar(25) null, age smallint null) engine InnoDB");
        } catch (SQLException e) {
            System.out.println("Ошибка при вызове метода createUsersTable");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (final Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sqlRequest = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setInt(3, age);
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("при добавлении пользователя с именем: " + name + " ,фамилией " + lastName +
                        " в методе saveUser возникла ошибка");
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("при удалении пользователя (в методе saveUser) с именем: " + name + " ,фамилией "
                    + lastName + " Возникла ошибка соединения при вызове метода getConnection");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) { //черновик
        String sqlRequest = "DELETE FROM users WHERE id = ?";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest)) {
                preparedStatement.setLong(1, id);
                preparedStatement.execute();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("при удалении пользователя с id: " + id + " в методе removeUserById возникла ошибка");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("при удалении пользователя (в методе removeUserById) id: " + id + " Возникла ошибка " +
                    "соединения при вызове метода getConnection");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsersList = new ArrayList<>();
        String sqlRequest = "SELECT id, name, lastname, age FROM users";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age"));
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
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM users");
                connection.commit();
            } catch (SQLException e) {
                System.out.println("В методе cleanUsersTable возникла ошибка");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("В методе cleanUsersTable при вызове метода getConnection возникла ошибка");
            e.printStackTrace();
        }
    }
}
