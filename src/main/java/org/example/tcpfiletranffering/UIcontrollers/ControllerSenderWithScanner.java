package org.example.tcpfiletranffering.UIcontrollers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.tcpfiletranffering.Main;
import org.example.tcpfiletranffering.SocketHandling.DeviceScanner;
import org.example.tcpfiletranffering.SocketHandling.FTsender;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerSenderWithScanner implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField receiverIP;
    @FXML
    private Button selectFileButton, fileSendButton, scanButton;
    @FXML
    private Label selectedFile, sendingStatus, localIpLabel;
    @FXML
    private ListView<DeviceScanner.Device> deviceListView;
    @FXML
    private ProgressIndicator scanProgress;

    private String ip;
    private String filePath;
    private ObservableList<DeviceScanner.Device> deviceList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectFileButton.setOnAction(this::chooseFile);
        selectedFile.setPrefWidth(400);
        selectedFile.setMaxWidth(400);
        selectedFile.setWrapText(true);
        sendingStatus.setText("");
        
        // Initialize device list
        deviceList = FXCollections.observableArrayList();
        deviceListView.setItems(deviceList);
        
        // Hide progress indicator initially
        scanProgress.setVisible(false);
        
        // Display local IP
        String localIp = DeviceScanner.getLocalIpAddress();
        localIpLabel.setText("Your IP: " + localIp);
        
        // Handle device selection from list
        deviceListView.setOnMouseClicked(event -> {
            DeviceScanner.Device selected = deviceListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                receiverIP.setText(selected.getIpAddress());
            }
        });
    }

    public void scanForDevices(ActionEvent event) {
        scanProgress.setVisible(true);
        sendingStatus.setText("🔍 Scanning network for devices...");
        deviceList.clear();
        scanButton.setDisable(true);
        
        // Run scan in background thread
        Task<List<DeviceScanner.Device>> scanTask = new Task<>() {
            @Override
            protected List<DeviceScanner.Device> call() {
                return DeviceScanner.scanNetwork();
            }
        };
        
        scanTask.setOnSucceeded(e -> {
            List<DeviceScanner.Device> devices = scanTask.getValue();
            deviceList.addAll(devices);
            scanProgress.setVisible(false);
            scanButton.setDisable(false);
            
            if (devices.isEmpty()) {
                sendingStatus.setText("❌ No devices found. Make sure receiver is running.");
            } else {
                sendingStatus.setText("✅ Found " + devices.size() + " device(s). Click to select.");
            }
        });
        
        scanTask.setOnFailed(e -> {
            scanProgress.setVisible(false);
            scanButton.setDisable(false);
            sendingStatus.setText("❌ Scan failed: " + scanTask.getException().getMessage());
        });
        
        new Thread(scanTask).start();
    }

    public void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File");

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedFile.setText(file.getAbsolutePath());
            System.out.println("Selected file: " + file.getAbsolutePath());
        } else {
            selectedFile.setText("No file selected");
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
            sendingStatus.setText("❌ Please enter the receiver's IP address.");
            return;
        }
        if(filePath.isEmpty() || filePath.equals("No file selected")){
            sendingStatus.setText("❌ Please select a file to send.");
            return;
        }

        try{
            sendingStatus.setText("📤 Sending file...");
            System.out.println("Connecting to: " + ip + ":5001");
            FTsender.handleSend(ip,5001,filePath);
            sendingStatus.setText("✅ File sent successfully!");
        }catch (Exception e){
            e.printStackTrace();
            sendingStatus.setText("❌ Failed to send the file: " + e.getMessage());
        }
    }
}
