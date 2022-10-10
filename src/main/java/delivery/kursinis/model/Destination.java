package delivery.kursinis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Destination {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String address;
    private LocalDate requestedDeliveryDate;
    private Double distance;

    @OneToOne
    private Orders orders;
//    private LocalDate deliveryStartDate;
//    private LocalDate deliveryEndDate;
//    private boolean isDelivering;
//    private String status;
//    private Double averageEmission;
//    private Double averageSpeed;

//    private Courier courier;
//    private Cargo cargo;
//    private Manager manager;
//    private ArrayList<Checkpoint> checkpoints;
    // Responsible managers
}
