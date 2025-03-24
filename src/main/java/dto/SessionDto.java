package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class SessionDto {
    private String sessionId;
    private String patientId;
    private String programName;
    private String therapistId;
    private String sessionDate;
}
