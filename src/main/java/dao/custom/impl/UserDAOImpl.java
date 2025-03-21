package dao.custom.impl;

import config.FactoryConfiguration;
import dao.custom.UserDAO;
import entity.Patient;
import entity.User;
import jakarta.persistence.NoResultException;
import javafx.scene.control.Alert;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean registerUser(User user) {
        boolean isRegistered;
        Session session = factoryConfiguration.getSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
            isRegistered = true;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return isRegistered;
    }

    @Override
    public User loginUser(String username) {
        Session session = factoryConfiguration.getSession();
        User user = null;
        try {
            String hql = "FROM User WHERE username = :username";
            user = (User) session.createQuery(hql).setParameter("username", username).uniqueResult();
            session.close();
            return user;
        } catch (NoResultException e) {
            // Handle the case where no result is found
            System.out.println("No user found with username: " + username);
        } catch (Exception e) {
            e.printStackTrace(); // Log any other exceptions
        } finally {
            if (session != null) {
                session.close(); // Ensure the session is closed
            }
        }
        return user;
    }

    @Override
    public boolean ifHaveAdmin() {
        boolean ishaveadmins = false;
        Session session = FactoryConfiguration.getInstance().getSession();
        String hql = "from User where role = 'admin'";
        try {
            ishaveadmins = session.createQuery(hql).list().size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ishaveadmins;
    }

    @Override
    public User search(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        User user = null;

        try {
            String hql = "FROM User WHERE id = :id";
            user = session.createQuery(hql, User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Handle the case where no result is found
            System.out.println("No user found with ID: " + id);
            new Alert(Alert.AlertType.ERROR, "User not found").show();
        } catch (Exception e) {
            e.printStackTrace(); // Log any other exceptions
        } finally {
            if (session != null) {
                session.close(); // Ensure the session is closed
            }
        }
        return user; // Will return null if no user is found
    }

    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        String hql = "FROM User";
        return session.createQuery(hql, User.class ).list();

    }

    @Override
    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        Session session  = factoryConfiguration.getSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        return true;
    }


    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();
        session.delete(session.get(User.class, id));
        session.getTransaction().commit();
        session.close();
        return true;
    }
}