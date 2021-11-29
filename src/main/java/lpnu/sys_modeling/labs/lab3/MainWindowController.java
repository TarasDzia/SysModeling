package lpnu.sys_modeling.labs.lab3;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class MainWindowController {
    @FXML
    private TextField numberOfTactsField;

    @FXML
    private TextArea textArea;

    Automaton automaton1;

    @FXML
    void startSimulation(MouseEvent event) {
        if (!isAllFieldsGood()) {
            showWarningMassage();
            return;
        }
        int tacts = Integer.parseInt(numberOfTactsField.getText());
        int states = 7;
        automaton1 = new Automaton(tacts);
        automaton1.start();
        textArea.setText(automaton1.getTransitionLog());
        automaton1.displayB();
        displayBeingInStateLikelihood();
        displayOneSignalLikelihood();
    }

    private void displayOneSignalLikelihood() {
        float[] array = automaton1.calculateLikelihoodInState();
        float likelihood = automaton1.calculateLikelihoodOfInputOneSignal(array);
        textArea.setText(textArea.getText() + "Ймовірність отримати на виході автомата сигнал, рівний 1 = " + likelihood + "\n");
    }

    private void displayBeingInStateLikelihood(){
        float[] array = automaton1.calculateLikelihoodInState();
        for (int i = 0; i < array.length; i++) {
            textArea.setText(textArea.getText() + "Ймовірність перебування автомата у стані z" + i + " = " + array[i] + "\n");
        }
    }

    public Boolean isAllFieldsGood() {
        TextField[] fields = new TextField[]{numberOfTactsField};

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