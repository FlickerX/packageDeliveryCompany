package delivery.kursinis.model;

import delivery.kursinis.hibernate.UserHib;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.Month;

public class main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PackageDeliveryCompany");

        UserHib userHib = new UserHib(entityManagerFactory);

        Manager manager = new Manager("admin", "admin", "Nikita", "Gorcakovas", LocalDate.of(2001, Month.SEPTEMBER, 14), "+37000000000", 567.00, false);
        userHib.createUser(manager);
    }
}
