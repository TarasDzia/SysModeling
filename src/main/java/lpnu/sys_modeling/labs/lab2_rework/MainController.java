package lpnu.sys_modeling.labs.lab2_rework;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class MainController {
    @FXML
    private TextField enterMinField;

    @FXML
    private TextField enterMaxField;

    @FXML
    private TextField workMinField;

    @FXML
    private TextField workMaxField;

    @FXML
    private TextField capacity;

    @FXML
    private TextArea textArea;

    @FXML
    void startSimulation(MouseEvent event) {
        if (!isAllFieldsGood()) {
            showWarningMassage();
            return;
        }
        int capacity = Integer.parseInt(this.capacity.getText());
        int minEnterTime = Integer.parseInt(this.enterMinField.getText());
        int maxEnterTime = Integer.parseInt(this.enterMaxField.getText());
        int minWorkTime = Integer.parseInt(this.workMinField.getText());
        int maxWorkTime = Integer.parseInt(this.workMaxField.getText());

        SimulationQueue simulationQueue = new SimulationQueue(capacity, minEnterTime, maxEnterTime, minWorkTime, maxWorkTime);
        simulationQueue.start();
        textArea.setText(simulationQueue.printResults());
    }

    public Boolean isAllFieldsGood() {
        TextField[] fields = new TextField[]{enterMinField, enterMaxField, workMinField, workMaxField, capacity};

        for (TextField f : fields)
            if (f.getText().isEmpty())
                return false;

        return true;
    }

    static void showWarningMassage() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Будь ласка перевірте чи всі поля заповнені.", ButtonType.OK);
        alert.show();
    }

}
