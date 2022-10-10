package delivery.kursinis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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

    @ManyToMany(mappedBy = "managers", cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //TODO: Make it work
    private List<Destination> destinations;

    public Manager(String login, String password, String name, String surname, LocalDate birthday, String phoneNo, Double salary, boolean isAdmin) {
        super(login, password, name, surname, birthday, phoneNo, salary);
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return  getName() + " " +
                getSurname() +
                ", Username = " + getLogin() +
                ", Admin = " + isAdmin;
    }
}
