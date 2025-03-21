package dao.custom.impl;

import config.FactoryConfiguration;
import dao.custom.ProgramDAO;
import entity.Program;
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
}
