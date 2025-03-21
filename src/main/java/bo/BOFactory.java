package bo;

import bo.custom.impl.PatientBOImpl;
import bo.custom.impl.ProgramBOImpl;
import bo.custom.impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {}
    public static BOFactory getInstance() {
        return boFactory==null?boFactory=new BOFactory():boFactory;
    }
    public enum BOType {
        PATIENT, PROGRAM, USER

    }
    public SuperBO getBO(BOType type) {
        switch (type) {
            case USER:
                return new UserBOImpl();
            case PATIENT:
                return new PatientBOImpl();
            case PROGRAM:
                return new ProgramBOImpl();
            default:
                return null;
        }
    }
}
