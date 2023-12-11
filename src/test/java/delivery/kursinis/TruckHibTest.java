package delivery.kursinis;

import delivery.kursinis.hibernate.TruckHib;
import delivery.kursinis.model.Truck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.CollectionUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class TruckHibTest extends BaseUnitTest {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PackageDeliveryCompanyTest");
    private TruckHib truckHib;

    @BeforeEach
    public void setUp() {
        super.setUp();
        truckHib = new TruckHib(entityManagerFactory);
    }

    @Test
    public void createTruckTest() {
        Truck truck = new Truck("mark", "model", 2.3, 100, "red");
        truckHib.createTruck(truck);
        var truckList = truckHib.getAllTrucks();
        if(truckList.isEmpty()) {
            throw new RuntimeException();
        }
    }

    @Test
    public void getAllTrucksTest() {
        Truck truck = new Truck("mark", "model", 2.3, 100, "red");
        Truck truck1 = new Truck("mark1", "model1", 20.3, 10, "red1");
        saveAllTrucks(List.of(truck, truck1));

        var truckList = truckHib.getAllTrucks();
        if(truckList.size() != 2) {
            throw new RuntimeException();
        }
    }

    @Test
    public void removeTruckTest() {
        Truck truck = new Truck("mark", "model", 2.3, 100, "red");
        truckHib.createTruck(truck);
        var truckList = truckHib.getAllTrucks();

        if(truckList.size() != 1) {
            throw new RuntimeException();
        }
        
        truckHib.removeTruck(truck);
        truckList = truckHib.getAllTrucks();
        if(!truckList.isEmpty()) {
            throw new RuntimeException();
        }
    }

    @Test
    public void updateTruckTest() {
        Truck truck = new Truck("mark", "model", 2.3, 100, "red");
        truckHib.createTruck(truck);
        var truckList = truckHib.getAllTrucks();

        if(truckList.size() != 1) {
            throw new RuntimeException();
        }
        Truck savedTruck = (Truck) truckList.get(0);
        savedTruck.setColor("Blue");
        truckHib.updateTruck(savedTruck);

        savedTruck = (Truck) truckHib.getAllTrucks().get(0);
        if(!savedTruck.getColor().equals("Blue")) {
            throw new RuntimeException();
        }
    }


    @AfterEach
    public void afterEach() {
        var truckList = (ArrayList<Truck>) truckHib.getAllTrucks();
        truckList.forEach(truck -> truckHib.removeTruck(truck));
    }
    private void saveAllTrucks(List<Truck> truckList) {
        truckList.forEach(truck -> {
            truckHib.createTruck(truck);
        });
    }
}
