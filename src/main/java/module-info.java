module lpnu.sys_modeling.labs{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    //lab3
    opens lpnu.sys_modeling.labs.lab3 to javafx.fxml;
    exports lpnu.sys_modeling.labs.lab3;
    //lab2
    opens lpnu.sys_modeling.labs.lab2 to javafx.fxml;
    exports lpnu.sys_modeling.labs.lab2;
    opens lpnu.sys_modeling.labs.lab2.objects to javafx.fxml;
    exports lpnu.sys_modeling.labs.lab2.objects;
    opens lpnu.sys_modeling.labs.lab2.threads to javafx.fxml;
    exports lpnu.sys_modeling.labs.lab2.threads;
    //rework
    opens lpnu.sys_modeling.labs.lab2_rework to javafx.fxml;
    exports lpnu.sys_modeling.labs.lab2_rework;
    opens lpnu.sys_modeling.labs.lab2_rework.objects to javafx.fxml;
    exports lpnu.sys_modeling.labs.lab2_rework.objects;
}