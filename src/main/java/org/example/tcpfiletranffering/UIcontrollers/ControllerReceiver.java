package org.example.tcpfiletranffering.UIcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.tcpfiletranffering.Main;
import org.example.tcpfiletranffering.SocketHandling.FTreceiver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerReceiver implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;

    private String filePath;
    private String fileName;


    @FXML
    private Button buttonSaveLocation,buttonStartConnection;

    @FXML
    private Label selectedLocation,receivedStatus;

    @FXML
    private TextField savingName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonSaveLocation.setOnAction(this::chooseDestination);
        selectedLocation.setPrefWidth(400);
        selectedLocation.setMaxWidth(400);
        selectedLocation.setWrapText(true);
    }

    public void chooseDestination(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a Destination");

        File file = directoryChooser.showDialog(null);
        if (file != null) {
            selectedLocation.setText(file.getAbsolutePath()+"\\");
            System.out.println("Selected location: " + file.getAbsolutePath());
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("selectBetween.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void startConnection(ActionEvent event){

        filePath = selectedLocation.getText()+savingName.getText();
        if(filePath.isEmpty()){
            receivedStatus.setText("Please select a destination");
            return;
        }

        try{
           receivedStatus.setText("connection started, waiting for file...");
        FTreceiver.handleReceive(5001,filePath);
           receivedStatus.setText("File received successfully" );}
        catch (Exception e){
            receivedStatus.setText("Error receiving file: " + e.getMessage());
        }
    }


}
