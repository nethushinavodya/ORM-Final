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
public class Therapist_Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long therapist_programId;

    @ManyToOne
    private Therapist therapist;

    @ManyToOne
    private Program program;

    public Therapist_Program(Therapist therapist, Program program) {
        this.therapist = therapist;
        this.program = program;
    }
}
