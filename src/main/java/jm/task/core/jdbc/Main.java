package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        UserService userDao = new UserServiceImpl();

        userDao.createUsersTable();

        userDao.saveUser("Zaur", "Tregulov", (byte) 28);
        userDao.saveUser("Студент", "Kata", (byte) 37);
        userDao.saveUser("Dmitriy", "Belousov", (byte) 37);
        userDao.saveUser("Olga", "Princess", (byte) 29);

        List<User> allUsersList = userDao.getAllUsers();
        System.out.println(allUsersList.toString());

        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
