package dao.custom.impl;

import config.FactoryConfiguration;
import dao.custom.PaymentDAO;
import entity.Payment;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    @Override
    public List<Payment> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public boolean save(Payment entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Payment entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }


    @Override
    public Payment search(String sessionId) {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();
        String hql = "FROM Payment WHERE therapy_session.sessionId = :sessionId";
        try {
            Payment payment = session.createQuery(hql, Payment.class).setParameter("sessionId", sessionId).getSingleResult();
            session.getTransaction().commit();
            session.close();
            return payment;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean pay(String paymentId, String payingAmount){
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();
        Payment payment = session.get(Payment.class, paymentId);
        payment.setRemainingAmount(payment.getRemainingAmount() - Double.parseDouble(payingAmount));
        session.update(payment);
        session.getTransaction().commit();
        session.close();
        return true;

    }

    @Override
    public List<Payment> getAllPatients(String patientId) {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();
        String hql = "FROM Payment WHERE therapy_session.patients.id = :patientId";
        List<Payment> payments = session.createQuery(hql, Payment.class).setParameter("patientId", patientId).getResultList();
        session.getTransaction().commit();
        session.close();
        return payments;
    }
}
