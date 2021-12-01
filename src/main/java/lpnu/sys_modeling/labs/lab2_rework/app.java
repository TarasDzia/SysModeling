package lpnu.sys_modeling.labs.lab2_rework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class app extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("main_window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 944, 600);
        stage.setTitle("lab2_rework");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}