package org.example.tcpfiletranffering.UIcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.tcpfiletranffering.Main;

import java.io.IOException;

public class ControllerWelcomePage {

    @FXML
    Button startButton;

    @FXML
    ImageView horseImage;

    private Parent root;
    private Stage stage;
    private Scene scene;


    public void start(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("selectBetween.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


}
