package delivery.kursinis.fxContorllers;

import delivery.kursinis.model.Checkpoint;
import delivery.kursinis.model.Destination;
import delivery.kursinis.model.Truck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Statistics implements Initializable {
    @FXML
    public BarChart checkpointsChart;
    @FXML
    public CategoryAxis checkpointsX;
    @FXML
    public NumberAxis checkpointsY;
    @FXML
    public Text totalCheckpoints;
    Destination destination;
    List<Checkpoint> checkpoints;
    Truck truck;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void initData(Destination destination, List<Checkpoint> checkpoints){
        this.destination = destination;
        this.checkpoints = checkpoints;
        HashMap<String, Integer> dates = new HashMap<>();
        XYChart.Series set = new XYChart.Series<>();
        for (Checkpoint checkpoint: checkpoints){
            String checkpointDate = String.valueOf(checkpoint.getCheckpointDate());
            if (dates != null && dates.containsKey(checkpointDate)){
                dates.put(checkpointDate, dates.get(checkpointDate) + 1);
            }else{
                dates.put(checkpointDate, 1);
            }
            set.getData().add(new XYChart.Data(checkpointDate, dates.get(checkpointDate)));
        }
        checkpointsChart.getData().addAll(set);
        totalCheckpoints.setText(String.valueOf(checkpoints.size()));
    }
}
