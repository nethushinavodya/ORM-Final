package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TherapyProgramHistoryDTO {
    private String programId;
    private String programName;
    private String therapistName;
    private String fee;
    private double remainingAmount;
    private String paymentDate;
}