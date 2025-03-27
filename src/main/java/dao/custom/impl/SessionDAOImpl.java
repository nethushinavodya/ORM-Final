package dao.custom.impl;

import config.FactoryConfiguration;
import dao.custom.SessionDAO;
import dto.Therapy_SessionDto;
import entity.Program;
import entity.Therapist;
import entity.Therapy_Session;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class SessionDAOImpl implements SessionDAO {
    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Therapy_Session> getAll() throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        String hql = "FROM Therapy_Session";
        return session.createQuery(hql, Therapy_Session.class).list();
    }

    @Override
    public boolean save(Therapy_Session entity) throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(Therapy_Session entity) throws SQLException, ClassNotFoundException {
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
        session.delete(session.get(Therapy_Session.class, id));
        session.getTransaction().commit();
        return true;

    }

    @Override
    public String search(String programId) {
        Session session = factoryConfiguration.getSession();
        System.out.println("programId = " + programId);

        try {
            Program program = session.get(Program.class, programId);

            String hql = "SELECT tp.therapist FROM Therapist_Program tp WHERE tp.program = :program";
            return session.createQuery(hql, Therapist.class)
                                .setParameter("program", program)
                                .getSingleResult()
                                .getTherapistId();
        } catch (NoResultException e) {
            return null;
        } finally {
            session.close();
        }
    }

}
