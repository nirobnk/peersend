module org.example.tcpfiletranffering {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tcpfiletranffering to javafx.fxml;
    exports org.example.tcpfiletranffering;
    exports org.example.tcpfiletranffering.UIcontrollers;
    opens org.example.tcpfiletranffering.UIcontrollers to javafx.fxml;
}