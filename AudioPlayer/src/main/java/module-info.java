module com.example.audioplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens hi.vidmot to javafx.fxml;
    exports hi.vidmot;
}
