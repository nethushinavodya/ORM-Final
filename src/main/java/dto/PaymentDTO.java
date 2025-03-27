package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PaymentDTO {
    private long paymentId;
    private String paymentDetails;
    private double fullAmount;
    private double remainingAmount;

    public PaymentDTO(String paymentDetails, double fullAmount, double remainingAmount) {
        this.paymentDetails = paymentDetails;
        this.fullAmount = fullAmount;
        this.remainingAmount = remainingAmount;
    }

}
