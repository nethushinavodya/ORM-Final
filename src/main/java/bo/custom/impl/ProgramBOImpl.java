package bo.custom.impl;

import bo.custom.ProgramBO;
import dao.DAOFactory;
import dao.custom.ProgramDAO;
import dto.ProgramDto;
import entity.Program;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramBOImpl implements ProgramBO {
    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PROGRAM);
    @Override
    public ObservableList<ProgramDto> getAllPrograms() throws SQLException, ClassNotFoundException {
        List<ProgramDto> programDtos = new ArrayList<>();
        List<Program> programs = programDAO.getAll();
        for (Program program : programs) {
            programDtos.add(new ProgramDto(program.getProgramId(),program.getName(),program.getDuration(),program.getFee()));
        }
        return FXCollections.observableArrayList(programDtos);
    }

    @Override
    public boolean addProgram(ProgramDto programDto) throws SQLException, ClassNotFoundException {
        return programDAO.save(new Program(programDto.getProgramId(),programDto.getName(),programDto.getDuration(),programDto.getFee()));
    }

    @Override
    public boolean updateProgram(ProgramDto programDto) throws SQLException, ClassNotFoundException {
        return programDAO.update(new Program(programDto.getProgramId(),programDto.getName(),programDto.getDuration(),programDto.getFee()));
    }

    @Override
    public boolean deleteProgram(String programId) throws SQLException, ClassNotFoundException {
        return  programDAO.delete(programId);
    }
}
