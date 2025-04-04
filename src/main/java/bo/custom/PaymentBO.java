package bo.custom;

import bo.SuperBO;
import dto.PaymentDTO;

public interface PaymentBO extends SuperBO {

    PaymentDTO searchPayment(String sessionId);

    boolean pay(String paymentId, String paymentAmount);
}
