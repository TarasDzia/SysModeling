module lpnu.sys_modeling.labs.lab3s{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens lpnu.sys_modeling.labs.lab3 to javafx.fxml;
    exports lpnu.sys_modeling.labs.lab3;
}