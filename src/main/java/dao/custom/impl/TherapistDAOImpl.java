package dao.custom.impl;

import config.FactoryConfiguration;
import dao.custom.TherapistDAO;
import dto.TherapistDto;
import entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

    public List<String> getAvailableTherapists() {
        Session session = factoryConfiguration.getSession();
        String hql = "FROM Therapist WHERE status = 'Available'";
        return session.createQuery(hql, Therapist.class).list().stream().map(Therapist::getTherapistId).toList();
    }
    @Override
    public boolean update(Therapist entity) throws SQLException, ClassNotFoundException {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();

        session.update(entity);
        session.getTransaction().commit();
        return true;

    }

    @Override
    public void updateStatus(TherapistDto therapistDTO){
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Query query = session.createQuery("UPDATE Therapist SET status = :status WHERE therapistId = :therapistId");
            query.setParameter("status", therapistDTO.getStatus());
            query.setParameter("therapistId", therapistDTO.getTherapistId());
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Serializable updatetherapist(Therapist therapist, Session session) {
        session.update(therapist);
        return therapist.getTherapistId();
    }

}
