module com.example.frdmclub {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.frdmclub to javafx.fxml;
    exports com.example.frdmclub;
}