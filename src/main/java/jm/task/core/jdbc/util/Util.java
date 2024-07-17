package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static SessionFactory sessionFactory;

    private Util() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties setting = new Properties();
                setting.put("hibernate.connection.driver_class", DRIVER);
                setting.put("hibernate.connection.url", DB_URL);
                setting.put("hibernate.connection.username", DB_USER);
                setting.put("hibernate.connection.password", DB_PASSWORD);
                setting.put(Environment.SHOW_SQL, "true");
                setting.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                setting.put(Environment.HBM2DDL_AUTO, "none"); // "create-drop"

                configuration.setProperties(setting);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("Подключение есть");

            } catch (Exception e) {
                System.out.println("ошибка создания фабрики");
            }
        }
        return sessionFactory;
    }

        public static Connection getConnection () {
            Connection connection;
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (SQLException e) {
                System.out.println("Failed to connect to database");
                throw new RuntimeException(e);
            }
            return connection;
        }


    }
