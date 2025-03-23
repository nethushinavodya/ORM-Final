package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "program")
    private List<Therapist_Program> therapist_programs;

    public Program(String programId, String name, String duration, String fee) {
        this.programId = programId;
        this.name = name;
        this.duration = duration;
        this.fee = fee;
    }
}
