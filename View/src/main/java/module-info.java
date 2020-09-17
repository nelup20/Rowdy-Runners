module View {
    requires Model;
    requires javafx.controls;
    requires javafx.fxml;
    opens view to javafx.graphics, javafx.fxml;

}