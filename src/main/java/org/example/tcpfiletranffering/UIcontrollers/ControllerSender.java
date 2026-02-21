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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.tcpfiletranffering.Main;
import org.example.tcpfiletranffering.SocketHandling.FTsender;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSender implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField receiverIP;
    @FXML
    private Button selectFileButton,fileSendButton;
    @FXML
    private Label selectedFile,sendingStatus;

    private String ip;
    private String filePath;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

     selectFileButton.setOnAction(this::chooseFile);
        selectedFile.setPrefWidth(400);
        selectedFile.setMaxWidth(400);
        selectedFile.setWrapText(true);
    }

    public void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File");

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
                selectedFile.setText(file.getAbsolutePath());
                System.out.println("Selected file: " + file.getAbsolutePath());
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

    public void send(ActionEvent event){

        ip = receiverIP.getText();
        filePath = selectedFile.getText();

        if(ip.isEmpty()){
            sendingStatus.setText("Please enter the receiver's IP address.");
            return;
        }
        if(filePath.isEmpty()){
            sendingStatus.setText("Please select a file to send.");
            return;
        }

        try{
            FTsender.handleSend(ip,5001,filePath);
            sendingStatus.setText("File sent successfully!");
        }catch (Exception e){
            sendingStatus.setText("Failed to send the file: " + e.getMessage());
        }

    }


}
