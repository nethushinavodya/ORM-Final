package bo.custom.impl;

import bo.custom.SessionBO;
import config.FactoryConfiguration;
import dao.DAOFactory;
import dao.custom.SessionDAO;
import dto.PaymentDTO;
import dto.Therapy_SessionDto;
import entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.sql.SQLException;

public class SessionBOImpl implements SessionBO {
    SessionDAO sessionDAO = (SessionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SESSION);

    @Override
    public boolean addSession(Therapy_SessionDto therapySessionDto, PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            Therapist therapist = session.get(Therapist.class, therapySessionDto.getTherapistId());
            Patient patient = session.get(Patient.class, therapySessionDto.getPatientId());
            Program program = session.get(Program.class, therapySessionDto.getProgramId());

            Therapy_Session therapySession = new Therapy_Session(therapySessionDto.getSessionDate(), patient, program, therapist);
            Payment payment = new Payment(paymentDTO.getPaymentDetails(), paymentDTO.getFullAmount(), paymentDTO.getRemainingAmount(), therapySession);

            Serializable save = sessionDAO.save(therapySession);
            if (save != null) {
                session.save(payment);
                transaction.commit();
                return true;
            }else {
                transaction.rollback();
                session.close();
                return false;
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }


    @Override
    public String search(String programId) {
        return sessionDAO.search(programId);
    }

//    @Override
//    public ObservableList<Therapy_SessionDto> getAllSessions() throws SQLException, ClassNotFoundException {
//        List<Therapy_SessionDto> therapySessionDtos = new ArrayList<>();
//        List<Therapy_Session> therapySessions = sessionDAO.getAll();
//        for (Therapy_Session therapySession : therapySessions) {
//            System.out.println(therapySession.getSessionId());
//            System.out.println(therapySession.getPatients().getId());
//
//            therapySessionDtos.add(new Therapy_SessionDto(therapySession.getSessionId(),therapySession.getPatients().getId(),therapySession.getPrograms().getProgramId(),therapySession.getTherapist().getTherapistId(),therapySession.getSessionDate()));
//            System.out.println(Therapy_SessionDto.class);
//        }
//        return FXCollections.observableArrayList(therapySessionDtos);
//    }

    @Override
    public boolean updateSession(Therapy_SessionDto therapySessionDto) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Patient patient = session.get(Patient.class, therapySessionDto.getPatientId());
        Program program = session.get(Program.class, therapySessionDto.getProgramId());
        Therapist therapist = session.get(Therapist.class, therapySessionDto.getTherapistId());

        return sessionDAO.update(new Therapy_Session( therapySessionDto.getSessionDate(), patient, program, therapist));
    }

    @Override
    public boolean deletePatient(String patientId) throws SQLException, ClassNotFoundException {
        return sessionDAO.delete(patientId);
    }
}
