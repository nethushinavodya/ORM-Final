package bo.custom;

import bo.SuperBO;
import dto.ProgramDto;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface ProgramBO extends SuperBO {
    ObservableList<ProgramDto> getAllPrograms() throws SQLException, ClassNotFoundException;

    boolean addProgram(ProgramDto programDto) throws SQLException, ClassNotFoundException;

    boolean updateProgram(ProgramDto programDto) throws SQLException, ClassNotFoundException;

    boolean deleteProgram(String programId) throws SQLException, ClassNotFoundException;

    ProgramDto searchProgram(String programId);
}
