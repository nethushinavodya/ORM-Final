package dao.custom;

import dao.CrudDAO;
import entity.Payment;

import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {

    Payment search(String sessionId);

    boolean pay(String paymentId, String paymentAmount);

    List<Payment> getAllPatients(String patientId);
}
