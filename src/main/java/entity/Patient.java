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
public class Patient {
    @Id
    private String id;
    private String name;
    private String email;
    private String address;
    private String tel;

    @OneToMany(mappedBy = "patients")
    private List<Therapy_Session> therapy_sessions;

    public Patient(String id, String name, String email, String address, String tel) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.tel = tel;
    }
}
