package bo.custom.impl;

import bo.custom.TherapistBO;
import config.FactoryConfiguration;
import dao.DAOFactory;
import dao.custom.ProgramDAO;
import dao.custom.TherapistDAO;
import dto.TherapistDto;
import entity.Program;
import entity.Therapist;
import entity.Therapist_Program;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapistBOImpl implements TherapistBO {
    TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPIST);
    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PROGRAM);
    @Override
    public ObservableList<TherapistDto> getAllTherapists() throws SQLException, ClassNotFoundException {
        List<TherapistDto> therapistDtos = new ArrayList<>();
        List<Therapist> therapists = therapistDAO.getAll();
        for (Therapist therapist : therapists) {
            therapistDtos.add(new TherapistDto(therapist.getTherapistId(), therapist.getName(), therapist.getSpecialization(), therapist.getContactNo(), therapist.getStatus()));
        }
        return FXCollections.observableArrayList(therapistDtos);
    }

    @Override
    public void addTherapist(TherapistDto therapistDTO, String programId) throws SQLException, ClassNotFoundException {
        //transaction
        Therapist therapist = new Therapist(therapistDTO.getTherapistId(), therapistDTO.getName(), therapistDTO.getSpecialization(), therapistDTO.getContactNo(), therapistDTO.getStatus());

        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();

        try {
            if (programId == null) {
                therapistDAO.save(therapist);
                session.getTransaction().commit();
            } else {
                Program program = programDAO.get(programId);
                Therapist_Program therapist_program = new Therapist_Program(therapist, program);
                Serializable id = therapistDAO.savetherapist(therapist, session);
                if (id != null) {
                    session.save(therapist_program);
                    session.getTransaction().commit();
                }
            }
        } catch (SQLException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }


    @Override
    public boolean deleteTherapist(String id) throws SQLException, ClassNotFoundException {
        return therapistDAO.delete(id);
    }


    @Override
    public boolean updateTherapist(TherapistDto therapistDTO, String programId) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            Therapist therapist = new Therapist(therapistDTO.getTherapistId(), therapistDTO.getName(), therapistDTO.getSpecialization(), therapistDTO.getContactNo(), therapistDTO.getStatus());

            if (programId == null) {
                therapistDAO.update(therapist);
                session.getTransaction().commit();
                therapistDTO.setStatus("Available");
            } else {
                Program program = programDAO.get(programId);
                Therapist_Program therapist_program = new Therapist_Program(therapist, program);
                System.out.println("therapist_program = " + therapist_program);
                Serializable id = therapistDAO.updatetherapist(therapist, session);
                if (id != null) {
                    session.update(therapist_program);
                    session.getTransaction().commit();
                    therapistDTO.setStatus("Not Available");
                }
            }
            therapistDAO.updateStatus(therapistDTO);
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }
    @Override
    public List<String> getAvailableTherapistIds() {
        return therapistDAO.getAvailableTherapists();
    }


}
