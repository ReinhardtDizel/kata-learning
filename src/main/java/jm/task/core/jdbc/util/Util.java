package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties prop = new Properties();

            prop.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            prop.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/test?autoReconnect=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
            prop.setProperty(Environment.USER, "root");
            prop.setProperty(Environment.PASS, "59333");
            prop.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            prop.setProperty(Environment.HBM2DDL_AUTO, "create-drop");
            prop.setProperty(Environment.SHOW_SQL, "true");
            prop.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            prop.setProperty(Environment.C3P0_MIN_SIZE, "5");
            prop.setProperty(Environment.C3P0_MAX_SIZE, "200");
            prop.setProperty(Environment.C3P0_MAX_STATEMENTS, "200");

            Configuration configuration = new Configuration();
            configuration.setProperties(prop);
            configuration.addAnnotatedClass(User.class);

            sessionFactory = configuration.buildSessionFactory(new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build());

        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSession() throws HibernateException {
        return sessionFactory;
    }


    public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {

        String userName = "root";
        String password = "59333";

        Class.forName("com.mysql.cj.jdbc.Driver");
        String connectionURL = "jdbc:mysql://localhost:3306/test?useLegacyDatetimeCode=false&serverTimezone=UTC";

        return DriverManager.getConnection(connectionURL, userName, password);
    }
}
