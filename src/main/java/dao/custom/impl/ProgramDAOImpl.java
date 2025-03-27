package dao.custom.impl;

import config.FactoryConfiguration;
import dao.custom.ProgramDAO;
import entity.Patient;
import entity.Program;
import jakarta.persistence.NoResultException;
import javafx.scene.control.Alert;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class ProgramDAOImpl implements ProgramDAO {
    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public List<Program> getAll() throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        String hql = "FROM Program";
        return session.createQuery(hql, Program.class).list();
    }

    @Override
    public boolean save(Program entity) throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();

        session.save(entity);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(Program entity) throws SQLException, ClassNotFoundException {
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

        session.delete(session.get(Program.class, id));
        session.getTransaction().commit();
        return true;
    }

    @Override
    public Program get(String programId) {
        Session session = factoryConfiguration.getSession();
        return session.get(Program.class, programId);
    }

    @Override
    public Program search(String programId) {
        Session session = factoryConfiguration.getSession();
        Program program = null;

        try {
            String hql = "FROM Program WHERE programId = :programId";
            program = session.createQuery(hql, Program.class)
                    .setParameter("programId", programId)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Handle the case where no result is found
            System.out.println("No program found with ID: " + programId);
            new Alert(Alert.AlertType.ERROR, "Program not found").show();
        } catch (Exception e) {
            e.printStackTrace(); // Log any other exceptions
        } finally {
            if (session != null) {
                session.close(); // Ensure the session is closed
            }
        }

        return program; // Will return null if no user is found
    }

}
