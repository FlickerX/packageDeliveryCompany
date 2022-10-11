package delivery.kursinis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Checkpoint {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String address;

    private LocalDate checkpointDate;

    @ManyToMany (mappedBy = "checkpoints", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Destination> destinations;

    public Checkpoint(String address, LocalDate checkpointDate) {
        this.address = address;
        this.checkpointDate = checkpointDate;
    }

    @Override
    public String toString() {
        return "Address= " + address +
                ", Date=" + checkpointDate;
    }
}
