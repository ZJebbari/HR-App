module com.jbc.javaproject1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.jfoenix;
    requires fontawesomefx;
    requires java.sql;
    requires lombok;

    opens com.jbc.javaproject to javafx.fxml;
    exports com.jbc.javaproject;
    exports com.jbc.javaproject.AdminstratorClass;
    opens com.jbc.javaproject.AdminstratorClass to javafx.fxml;
}