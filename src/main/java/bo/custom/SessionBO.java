package bo.custom;

import bo.SuperBO;
import dto.Therapy_SessionDto;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface SessionBO extends SuperBO {
//    ObservableList<Therapy_SessionDto> getAllSessions() throws SQLException, ClassNotFoundException;

    boolean addSession(Therapy_SessionDto therapySessionDto) throws SQLException, ClassNotFoundException;
    
    String search(String programId);

    boolean deleteSession(long sessionId) throws SQLException, ClassNotFoundException;

//    ObservableList<Therapy_SessionDto> getAllSessions() throws SQLException, ClassNotFoundException;

    boolean updateSession(Therapy_SessionDto therapySessionDto) throws SQLException, ClassNotFoundException;

//    boolean updateSession(String sessionDate);
}
