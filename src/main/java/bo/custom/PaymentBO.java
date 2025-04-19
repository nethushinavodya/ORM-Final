package bo.custom;

import bo.SuperBO;
import dto.PaymentDTO;
import javafx.collections.ObservableList;

public interface PaymentBO extends SuperBO {

    PaymentDTO searchPayment(String sessionId);

    boolean pay(String paymentId, String paymentAmount);

    ObservableList<PaymentDTO> getAllPayments(String patientId);
}
