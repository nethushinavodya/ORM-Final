package dao.custom.impl;

import dao.custom.SessionDAO;
import entity.Session;

import java.sql.SQLException;
import java.util.List;

public class SessionDAOImpl implements SessionDAO {
    @Override
    public List<Session> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public boolean save(Session entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Session entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
