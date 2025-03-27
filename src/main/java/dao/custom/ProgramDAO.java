package dao.custom;

import dao.CrudDAO;
import entity.Program;

import java.util.List;

public interface ProgramDAO extends CrudDAO<Program> {
    Program get(String programId);

    Program search(String programId);
}
