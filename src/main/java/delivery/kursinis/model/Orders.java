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
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate deliveryStartDate;
    private LocalDate deliveryEndDate;
    private boolean isDelivering;

//    @ManyToOne
//    private Manager manager; //TODO: Make it work

    @ManyToMany
    private List<Cargo> products;

    @ManyToMany
    private List<Checkpoint> checkpoints;

    @OneToOne
    private Courier courier;

    @OneToOne
    private Truck truck;

    @OneToOne
    private Destination destination;
    private OrderStatus status;
}
