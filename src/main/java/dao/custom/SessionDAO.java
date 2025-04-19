package dao.custom;

import dao.CrudDAO;
import entity.Therapy_Session;

import java.util.List;

public interface SessionDAO extends CrudDAO<Therapy_Session> {

    String search(String programId);

    List<String> getProgramIds(String patientId);

    Long searchSessionId(String patiendId, String programId);

    List<Object[]> getPatientTherapyHistory(String patientId);


//    boolean updateSession(String sessionDate);
}
