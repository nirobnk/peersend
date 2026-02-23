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
        receivedStatus.setText(""); // Initialize status as empty
    }

    public void chooseDestination(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a Destination");

        File file = directoryChooser.showDialog(null);
        if (file != null) {
            selectedLocation.setText(file.getAbsolutePath() + File.separator);
            System.out.println("Selected location: " + file.getAbsolutePath());
        } else {
            selectedLocation.setText("No location selected");
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

        if(selectedLocation.getText().isEmpty() || selectedLocation.getText().equals("No location selected")){
            receivedStatus.setText("❌ Please select a destination folder.");
            return;
        }
        if(savingName.getText().isEmpty()){
            receivedStatus.setText("❌ Please enter a file name.");
            return;
        }

        filePath = selectedLocation.getText() + savingName.getText();
        System.out.println("Full file path: " + filePath);

        try{
           receivedStatus.setText("⏳ Waiting for incoming file connection...");
           FTreceiver.handleReceive(5001,filePath);
           receivedStatus.setText("✅ File received successfully at: " + filePath);
        } catch (Exception e){
            e.printStackTrace();
            receivedStatus.setText("❌ Error receiving file: " + e.getMessage());
        }
    }


}
