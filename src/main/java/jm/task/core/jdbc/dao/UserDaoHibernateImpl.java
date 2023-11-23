package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }

    @Override
    //SQL
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE users (id int AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(45) NOT NULL,last_name VARCHAR(45),age TINYINT)");
            session.getTransaction().commit();
        } catch (NullPointerException npe) {
            System.out.println("Исключение: " + npe.getMessage());
        }
    }

    @Override
    //SQL
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users")
                .addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(name);
            session.save(lastName);
            session.save(age);
            tx1.commit();
        } catch (NullPointerException npe) {
            System.out.println("Исключение: " + npe.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (NullPointerException npe) {
            System.out.println("Исключение: " + npe.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list(); // может быть не надо писать User.class
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("truncate table User").executeUpdate();
            session.getTransaction().commit();
        } catch (NullPointerException npe) {
            System.out.println("Исключение: " + npe.getMessage());
        }
    }
}