package bo.custom.impl;

import bo.custom.PaymentBO;
import dao.DAOFactory;
import dao.custom.PaymentDAO;
import dto.PaymentDTO;
import dto.ProgramDto;
import entity.Payment;
import entity.Program;

import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);


    @Override
    public PaymentDTO searchPayment(String sessionId) {
        Payment payment = paymentDAO.search(sessionId);
        if (payment == null) {
            return null;
        } else {
            return new PaymentDTO(payment.getPaymentId(),payment.getPaymentDetails(),payment.getFullAmount(),payment.getRemainingAmount());
        }
    }

    @Override
    public boolean pay(String paymentId, String payingAmount) {
        return paymentDAO.pay(paymentId,payingAmount);
    }
}
