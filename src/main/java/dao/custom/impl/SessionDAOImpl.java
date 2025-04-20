package dao.custom.impl;

import config.FactoryConfiguration;
import dao.custom.SessionDAO;
import entity.Program;
import entity.Therapist;
import entity.Therapy_Session;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;

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
        String hql = "DELETE FROM Therapy_Session WHERE patients.id = :patientId";
        int result = session.createQuery(hql).setParameter("patientId", id).executeUpdate();
        session.getTransaction().commit();
        return result > 0;
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

    @Override
    public List<String> getProgramIds(String patientId) {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();
        String hql = "SELECT programs.programId FROM Therapy_Session WHERE patients.id = :patientId";
        List<String> programs = session.createQuery(hql, String.class)
                .setParameter("patientId", patientId)
                .list();
        System.out.println(programs);
        session.getTransaction().commit();
        session.close();

        return programs;

    }

    @Override
    public Long searchSessionId(String patiendId, String programId) {
        Session session = factoryConfiguration.getSession();
        session.beginTransaction();
        String hql = "SELECT sessionId FROM Therapy_Session WHERE patients.id = :patiendId AND programs.programId = :programId";
        Long sessionId = session.createQuery(hql, Long.class)
                .setParameter("patiendId", patiendId)
                .setParameter("programId", programId)
                .getSingleResult();
        session.getTransaction().commit();
        session.close();
        return sessionId;
    }

    @Override
    public List<Object[]> getPatientTherapyHistory(String patientId) {
        Session session = factoryConfiguration.getSession();
        try {
            String hql = "SELECT p.programId, p.name, t.name, p.fee, pay.remainingAmount, ts.sessionDate " +
                    "FROM Therapy_Session ts " +
                    "JOIN ts.programs p " +
                    "JOIN ts.therapist t " +
                    "LEFT JOIN Payment pay ON pay.therapy_session = ts " +
                    "WHERE ts.patients.id = :patientId " +
                    "ORDER BY ts.sessionDate DESC";

            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("patientId", patientId);

            return query.getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Therapy_Session> getAllPatients() {
        Session session = factoryConfiguration.getSession();
        String hql = "FROM Therapy_Session";
        return session.createQuery(hql, Therapy_Session.class).list();
    }

    @Override
    public List<String> getPatientIdsFromTherapySessions() {
        Session session = factoryConfiguration.getSession();
        String hql = "SELECT DISTINCT patients.id FROM Therapy_Session";
        return session.createQuery(hql, String.class).list();
    }
}
