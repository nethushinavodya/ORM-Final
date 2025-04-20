package bo.custom;

import bo.SuperBO;
import dto.PatientDto;
import dto.PaymentDTO;
import dto.TherapyProgramHistoryDTO;
import dto.Therapy_SessionDto;

import java.sql.SQLException;
import java.util.List;

public interface SessionBO extends SuperBO {
//    ObservableList<Therapy_SessionDto> getAllSessions() throws SQLException, ClassNotFoundException;

    boolean addSession(Therapy_SessionDto therapySessionDto, PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    
    String search(String programId);

//    ObservableList<Therapy_SessionDto> getAllSessions() throws SQLException, ClassNotFoundException;

    boolean updateSession(Therapy_SessionDto therapySessionDto) throws SQLException, ClassNotFoundException;

    boolean deletePatient(String patientId) throws SQLException, ClassNotFoundException;

    List<String> getProgramIds(String patientId);

    Long searchSessionId(String patiendId, String programId);

    List<TherapyProgramHistoryDTO> getPatientTherapyHistory(String patientId);

    List<String> getPatientIdsFromTherapySessions();


//    boolean updateSession(String sessionDate);
}
