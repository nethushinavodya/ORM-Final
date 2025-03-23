package bo.custom;

import bo.SuperBO;
import dto.TherapistDTO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface TherapistBO extends SuperBO {

    ObservableList<TherapistDTO> getAllTherapists() throws SQLException, ClassNotFoundException;

    void addTherapist(TherapistDTO therapistDTO, String programId) throws SQLException, ClassNotFoundException;

    boolean deleteTherapist(String id) throws SQLException, ClassNotFoundException;

    boolean updateTherapist(TherapistDTO therapistDTO) throws SQLException, ClassNotFoundException;
}
