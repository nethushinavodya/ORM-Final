package dao.custom.impl;

import config.FactoryConfiguration;
import dao.custom.PatientDAO;
import entity.Patient;
import jakarta.persistence.NoResultException;
import javafx.scene.control.Alert;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {
    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Patient> getAll() throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        String hql = "FROM Patient";
        return session.createQuery(hql, Patient.class).list();
    }

    @Override
    public boolean save(Patient entity) throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();

        session.save(entity);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(Patient entity) throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();

        session.update(entity);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();

        session.delete(session.get(Patient.class, id));
        session.getTransaction().commit();
        return true;
    }

    @Override
    public Patient search(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Patient patient = null;

        try {
            String hql = "FROM Patient WHERE id = :id";
            patient = session.createQuery(hql, Patient.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Handle the case where no result is found
            System.out.println("No patient found with ID: " + id);
            new Alert(Alert.AlertType.ERROR, "Patient not found").show();
        } catch (Exception e) {
            e.printStackTrace(); // Log any other exceptions
        } finally {
            if (session != null) {
                session.close(); // Ensure the session is closed
            }
        }

        return patient; // Will return null if no user is found
    }
}
