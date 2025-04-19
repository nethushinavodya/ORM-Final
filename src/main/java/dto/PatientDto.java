package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PatientDto {
    private String id;
    private String name;
    private String email;
    private String address;
    private String tel;
    private String registerDate;
}
