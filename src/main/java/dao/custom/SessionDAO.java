package dao.custom;

import dao.CrudDAO;
import dto.Therapy_SessionDto;
import entity.Therapy_Session;

import java.util.List;

public interface SessionDAO extends CrudDAO<Therapy_Session> {

    String search(String programId);

//    boolean updateSession(String sessionDate);
}
