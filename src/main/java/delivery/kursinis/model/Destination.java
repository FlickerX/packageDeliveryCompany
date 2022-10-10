package delivery.kursinis.model;

import delivery.kursinis.Enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private LocalDate deliveryStartDate;

    private LocalDate deliveryEndDate;

    private OrderStatus status;

    @ManyToMany
    private List<Manager> managers; //TODO: Make it work

    @ManyToMany
    private List<Cargo> cargos;

    @ManyToMany
    private List<Checkpoint> checkpoints;

    @OneToOne
    private Courier courier;

    @OneToOne
    private Truck truck;
}
