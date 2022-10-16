package delivery.kursinis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Truck {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String mark;
    private String model;
    private Double engineLiters;
    private Integer horsePower;
    private String color;
    private Boolean isAvailable;
    @OneToOne
    private Destination destination;

    public Truck(String mark, String model, Double engineLiters, Integer horsePower, String color) {
        this.mark = mark;
        this.model = model;
        this.engineLiters = engineLiters;
        this.horsePower = horsePower;
        this.color = color;
    }

    public Truck(int id, String mark, String model, Double engineLiters, Integer horsePower, String color) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.engineLiters = engineLiters;
        this.horsePower = horsePower;
        this.color = color;
    }

    @Override
    public String toString() {
        return mark + " " +
                model +
                ", Engine liters=" + engineLiters +
                ", Color='" + color;
    }
}
