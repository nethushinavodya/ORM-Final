package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;
    private String paymentDetails;
    private double fullAmount;
    private double remainingAmount;

    @OneToOne
    @JoinColumn(name = "sessionId")
    private Therapy_Session therapy_session;

    public Payment(String paymentDetails, double fullAmount, double remainingAmount, Therapy_Session therapy_session) {
        this.paymentDetails = paymentDetails;
        this.fullAmount = fullAmount;
        this.remainingAmount = remainingAmount;
        this.therapy_session = therapy_session;
    }
}
