package bo.custom;

import bo.SuperBO;
import dto.PaymentDTO;
import dto.Therapy_SessionDto;

import java.sql.SQLException;

public interface SessionBO extends SuperBO {
//    ObservableList<Therapy_SessionDto> getAllSessions() throws SQLException, ClassNotFoundException;

    boolean addSession(Therapy_SessionDto therapySessionDto, PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    
    String search(String programId);

//    ObservableList<Therapy_SessionDto> getAllSessions() throws SQLException, ClassNotFoundException;

    boolean updateSession(Therapy_SessionDto therapySessionDto) throws SQLException, ClassNotFoundException;

    boolean deletePatient(String patientId) throws SQLException, ClassNotFoundException;

//    boolean updateSession(String sessionDate);
}
