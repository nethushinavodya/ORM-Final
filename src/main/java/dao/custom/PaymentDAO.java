package dao.custom;

import dao.CrudDAO;
import entity.Payment;

public interface PaymentDAO extends CrudDAO<Payment> {

    Payment search(String sessionId);

    boolean pay(String paymentId, String paymentAmount);
}
