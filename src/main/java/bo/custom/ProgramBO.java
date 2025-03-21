package bo.custom;

import bo.SuperBO;
import dto.ProgramDto;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ProgramBO extends SuperBO {
    ObservableList<ProgramDto> getAllPrograms() throws SQLException, ClassNotFoundException;

    boolean addProgram(ProgramDto programDto) throws SQLException, ClassNotFoundException;
}
