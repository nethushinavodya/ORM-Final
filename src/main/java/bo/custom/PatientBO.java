package bo.custom;

import bo.SuperBO;
import dto.PatientDto;
import dto.UserDto;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface PatientBO extends SuperBO {
    ObservableList<PatientDto> getAllPatients() throws SQLException, ClassNotFoundException;

    boolean addPatient(PatientDto patientDto, UserDto userDto) throws SQLException, ClassNotFoundException;

    boolean updatePatient(PatientDto patientDto) throws SQLException, ClassNotFoundException;

    boolean deletePatient(String id) throws SQLException, ClassNotFoundException;

    PatientDto searchPatient(String id);
}
