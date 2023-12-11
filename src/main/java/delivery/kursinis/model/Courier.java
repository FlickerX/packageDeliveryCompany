package delivery.kursinis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Courier extends User{
    private String driverLicense;
    private String healthCertificate;

    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL)
    private List<Destination> destinations;

    public Courier(String login, String password, String name, String surname, LocalDate birthday, String phoneNo, Double salary, String driverLicense, String healthCertificate) {
        super(login, password, name, surname, birthday, phoneNo, salary);
        this.driverLicense = driverLicense;
        this.healthCertificate = healthCertificate;
    }

    @Override
    public String toString() {
        return getName() +
                " " + getSurname() +
                ", Username = " + getLogin();
    }
}
