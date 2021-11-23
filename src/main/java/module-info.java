module lpnu.labs.lab2.sysmodeling {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens lpnu.sys_modeling.labs.lab2 to javafx.fxml;
    exports lpnu.sys_modeling.labs.lab2;
    exports lpnu.sys_modeling.labs.lab2.objects;
    opens lpnu.sys_modeling.labs.lab2.objects to javafx.fxml;
    exports lpnu.sys_modeling.labs.lab2.threads;
    opens lpnu.sys_modeling.labs.lab2.threads to javafx.fxml;
}