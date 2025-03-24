package bo.custom;

import bo.SuperBO;
import dto.TherapistDto;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface TherapistBO extends SuperBO {

    ObservableList<TherapistDto> getAllTherapists() throws SQLException, ClassNotFoundException;

    void addTherapist(TherapistDto therapistDTO, String programId) throws SQLException, ClassNotFoundException;

    boolean deleteTherapist(String id) throws SQLException, ClassNotFoundException;

    boolean updateTherapist(TherapistDto therapistDTO) throws SQLException, ClassNotFoundException;
}
