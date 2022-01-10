package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {

        try (Session session = Util.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createSQLQuery("create table if not exists users " +
                    "(id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "lastname varchar(40) NOT NULL, " +
                    "age tinyint NOT NULL)").executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createSQLQuery("drop table if exists users;").executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = Util.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        }

    }

    @Override
    public void removeUserById(long id) {

        try (Session session = Util.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            System.out.println(user);
            session.delete(user);
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSession().openSession()) {
            return session.createQuery("SELECT a FROM User a", User.class).getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSession().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("truncate table users").executeUpdate();
            transaction.commit();
        }
    }
}
