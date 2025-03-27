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
public class Therapy_Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  sessionId;
    private String sessionDate;

    @ManyToOne
    private Patient patients;

    @ManyToOne
    private Program programs;

    @ManyToOne
    private Therapist therapist;

}
