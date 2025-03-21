package dao.custom;

import dao.CrudDAO;
import entity.Patient;

public interface PatientDAO extends CrudDAO<Patient> {
    Patient search(String id);
}
