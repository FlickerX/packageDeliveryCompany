package delivery.kursinis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double weight;

    @ManyToMany(mappedBy = "cargos", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Destination> destinations;
}
