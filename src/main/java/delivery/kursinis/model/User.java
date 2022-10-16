package delivery.kursinis.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@ToString
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String login;
    private String password;
    private String name;
    private String surname;
    private LocalDate birthday;
    private String phoneNo;
    private Double salary;


    public User(String login, String password, String name, String surname, LocalDate birthday, String phoneNo, Double salary) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.phoneNo = phoneNo;
        this.salary = salary;
    }

}
