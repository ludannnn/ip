package yoda.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import yoda.Yoda;

import java.io.IOException;

/**
 * A GUI for Yoda using FXML.
 */
public class MainApp extends Application {

    private Yoda yoda = new Yoda();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setYoda(yoda);  // inject the Yoda instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}