package entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class Therapist {
    @Id
    private String therapistId;
    private String name;
    private String specialization;
    private String contactNo;
    private String status;

    @OneToMany(mappedBy = "therapist")
    private List<Therapist_Program> therapist_programs;

    public Therapist(String therapistId, String name, String specialization, String contactNo, String status) {
        this.therapistId = therapistId;
        this.name = name;
        this.specialization = specialization;
        this.contactNo = contactNo;
        this.status = status;
    }
    @OneToMany(mappedBy = "therapist")
    private List<Therapy_Session> therapy_sessions;

    public Therapist(String therapistId, String name, String specialization, String contactNo, String status, List<Therapist_Program> therapist_programs) {
        this.therapistId = therapistId;
        this.name = name;
        this.specialization = specialization;
        this.contactNo = contactNo;
        this.status = status;
        this.therapist_programs = therapist_programs;
    }
}
