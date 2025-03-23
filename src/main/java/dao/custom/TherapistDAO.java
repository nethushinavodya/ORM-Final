package dao.custom;

import dao.CrudDAO;
import entity.Therapist;
import org.hibernate.Session;

import java.io.Serializable;

public interface TherapistDAO extends CrudDAO<Therapist> {
    Serializable savetherapist(Therapist therapist, Session session);
}
