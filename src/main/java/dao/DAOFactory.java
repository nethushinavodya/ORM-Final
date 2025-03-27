package dao;


import dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {
    }
    public static DAOFactory getInstance() {
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOType {
        PATIENT, PROGRAM, USER , SESSION, THERAPIST

    }
    public SuperDAO getDAO(DAOType type) {
        switch(type) {
            case USER:
                return new UserDAOImpl();
            case PATIENT:
                return new PatientDAOImpl();
            case PROGRAM:
                return new ProgramDAOImpl();
            case THERAPIST:
                return new TherapistDAOImpl();
            case SESSION:
                return new SessionDAOImpl();
            default:
                return null;
        }
    }
}
