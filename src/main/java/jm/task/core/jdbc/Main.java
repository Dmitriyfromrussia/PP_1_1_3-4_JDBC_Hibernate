package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        UserService action = new UserServiceImpl();

        action.createUsersTable();

        action.saveUser("Zaur", "Tregulov", (byte) 28);
        action.saveUser("Студент", "Kata", (byte) 37);
        action.saveUser("Dmitriy", "Belousov", (byte) 37);
        action.saveUser("Olga", "Princess", (byte) 29);

        List<User> allUsersList = action.getAllUsers();
        System.out.println(allUsersList.toString());

        action.cleanUsersTable();
        action.dropUsersTable();
    }
}