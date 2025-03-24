package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TherapistDto {
    private String TherapistId;
    private String name;
    private String specialization;
    private String contactNo;
    private String status;
}
