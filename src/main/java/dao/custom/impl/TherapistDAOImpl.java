package dao.custom.impl;

import config.FactoryConfiguration;
import dao.custom.TherapistDAO;
import entity.Therapist;
import org.hibernate.Session;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class TherapistDAOImpl implements TherapistDAO {
    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public List<Therapist> getAll() throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        String hql = "FROM Therapist";
        return session.createQuery(hql, Therapist.class).list();
    }

    @Override
    public boolean save(Therapist entity) throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();

        session.save(entity);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(Therapist entity) throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();
        String hql = "UPDATE Therapist SET name = :name, specialization = :specialization, contactNo = :contactNo WHERE therapistId = :therapistId";

        session.createQuery(hql)
                .setParameter("name", entity.getName())
                .setParameter("specialization", entity.getSpecialization())
                .setParameter("contactNo", entity.getContactNo())
                .setParameter("therapistId", entity.getTherapistId())
                .executeUpdate();
        session.getTransaction().commit();
        return true;

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();

        session.delete(session.get(Therapist.class,id));
        session.getTransaction().commit();
        return true;
    }

    @Override
    public Serializable savetherapist(Therapist therapist, Session session) {
        try {
          return (Serializable) session.save(therapist);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
