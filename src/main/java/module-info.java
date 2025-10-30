module edu.utsa.cs3443.froggyfinance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;


    opens edu.utsa.cs3443.froggyfinance to javafx.fxml;
    exports edu.utsa.cs3443.froggyfinance;
}