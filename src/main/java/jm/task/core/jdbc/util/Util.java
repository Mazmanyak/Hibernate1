package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static SessionFactory sessionFactory;

    private Util() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e.getMessage());
            }
        }
        return sessionFactory;
    }

    private static final String URL = "jdbc:mysql://localhost:3306/tz1.1.2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("нет подключения к БД\n" + e.getMessage());
        }
        return connection;
    }

}
/*
Подключение к базе данных берем отсюда (просто копируем и осмысливаем):
https://dzone.com/articles/hibernate-5-java-configuration-example
Меняем propertiesSetting.put(Environment.HBM2DDL_AUTO, "");   // теперь таблица не будет удаляться и
создаваться автоматически
+ в утиле не забываем создать метод для закрытия фабрики сессий.
даже если в решении не используете xml файл удалите (если вдруг изучали его).
 */
























