package dao;


import dao.custom.impl.PatientDAOImpl;
import dao.custom.impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {
    }
    public static DAOFactory getInstance() {
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOType {
        PATIENT, USER

    }
    public SuperDAO getDAO(DAOType type) {
        switch(type) {
            case USER:
                return new UserDAOImpl();
            case PATIENT:
                return new PatientDAOImpl();
            default:
                return null;
        }
    }
}
