package dao.custom;

import dao.CrudDAO;
import dto.TherapistDto;
import entity.Therapist;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public interface TherapistDAO extends CrudDAO<Therapist> {
    Serializable savetherapist(Therapist therapist, Session session);

    List<String> getAvailableTherapists();

    void updateStatus(TherapistDto therapistDTO);

    Serializable updatetherapist(Therapist therapist, Session session);
}
