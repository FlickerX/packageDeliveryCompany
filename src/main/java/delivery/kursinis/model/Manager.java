package delivery.kursinis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Entity
public class Manager extends User{
    private boolean isAdmin;
//    private LocalDate employmentDate;
//    private LocalDate endDate;

//    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL) //TODO: Make it work
//    private List<Orders> orders;

//    public Manager(String login, String password, String name, String surname, LocalDate birthday, String phoneNo, LocalDate employmentDate, LocalDate endDate) {
//        super(login, password, name, surname, birthday, phoneNo, salary);
//        this.employmentDate = employmentDate;
//        this.endDate = endDate;
////        this.orders = orders;
//    }

    public Manager(String login, String password, String name, String surname, LocalDate birthday, String phoneNo, Double salary, boolean isAdmin) {
        super(login, password, name, surname, birthday, phoneNo, salary);
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "Name = " + getName() +
                ", Surname = " + getSurname() +
                ", Username = " + getLogin() +
                ", Admin = " + isAdmin;
    }
}
