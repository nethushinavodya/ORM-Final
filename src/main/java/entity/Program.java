package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Program {
    @Id
    private String programId;
    private String name;
    private String duration;
    private String fee;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Therapist_Program> therapist_programs;

    public Program(String programId, String name, String duration, String fee) {
        this.programId = programId;
        this.name = name;
        this.duration = duration;
        this.fee = fee;
    }

    @OneToMany(mappedBy = "programs", cascade = CascadeType.ALL)
    private List<Therapy_Session> therapy_sessions;


    public Program(String programId, String name, String duration, String fee, List<Therapist_Program> therapist_programs) {
        this.programId = programId;
        this.name = name;
        this.duration = duration;
        this.fee = fee;
        this.therapist_programs = therapist_programs;
    }
}
