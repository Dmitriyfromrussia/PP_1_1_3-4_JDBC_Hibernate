package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;


import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (id int auto_increment primary key, " +
                        "name varchar(25) null, lastName varchar(25) null, age smallint null) engine InnoDB", User.class)
                .executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createNativeQuery("DROP TABLE IF EXISTS users", User.class).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = Util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ошибка транзакции в методе saveUser");
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.getTransaction().setRollbackOnly();
//        User user = session.get(User.class, id);// второй вариант, думаю менее оптимальный, тк создается объект класса
//        session.remove(user);
            session.createNativeQuery("DELETE FROM users WHERE id=:id", User.class)
                    .setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ошибка транзакции в методе removeUserById");
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> allUsers = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            allUsers = session.createNativeQuery("SELECT id, name, lastname, age FROM users", User.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ошибка транзакции в методе getAllUsers");
            e.printStackTrace();
        }
        return allUsers;
    }


    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("DELETE FROM users", User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ошибка транзакции в методе cleanUsersTable");
            e.printStackTrace();
        }
    }
}
