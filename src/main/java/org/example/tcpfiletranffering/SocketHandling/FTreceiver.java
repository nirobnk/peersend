package org.example.tcpfiletranffering.SocketHandling;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FTreceiver {
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    private static final String SCAN_REQUEST = "PEERSEND_SCAN";
    private static final String SCAN_RESPONSE = "PEERSEND_RECEIVER";
    private static final String FILE_TRANSFER = "PEERSEND_FILE";

    public static void handleReceive(int port, String directoryPath) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("listening to port:" + port);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println(clientSocket + " connected.");
                
                try {
                    dataInputStream = new DataInputStream(clientSocket.getInputStream());
                    dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                    
                    // Read the initial command
                    String command = dataInputStream.readUTF();
                    System.out.println("Received command: " + command);
                    
                    if (SCAN_REQUEST.equals(command)) {
                        // It's a scan request, respond and continue listening
                        System.out.println("Scan request received, responding...");
                        dataOutputStream.writeUTF(SCAN_RESPONSE);
                        dataOutputStream.flush();
                        dataInputStream.close();
                        dataOutputStream.close();
                        clientSocket.close();
                        System.out.println("Scan response sent, continuing to listen...");
                        
                    } else if (FILE_TRANSFER.equals(command)) {
                        // It's a file transfer, proceed with receiving
                        System.out.println("File transfer initiated");
                        receiveFile(directoryPath);
                        dataInputStream.close();
                        dataOutputStream.close();
                        clientSocket.close();
                        break; // Exit after receiving file
                        
                    } else {
                        // Unknown command, close connection
                        System.out.println("Unknown command, closing connection");
                        dataInputStream.close();
                        dataOutputStream.close();
                        clientSocket.close();
                    }
                } catch (Exception e) {
                    System.err.println("Error handling client: " + e.getMessage());
                    try {
                        clientSocket.close();
                    } catch (Exception ex) {
                        // Ignore
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void receiveFile(String directoryPath) throws Exception{
        int bytes = 0;
        
        // Receive filename first
        String fileName = dataInputStream.readUTF();
        System.out.println("Receiving file: " + fileName);
        
        // Create full path
        String fullPath = directoryPath + fileName;
        System.out.println("Saving file to: " + fullPath);
        
        FileOutputStream fileOutputStream = new FileOutputStream(fullPath);

        long size = dataInputStream.readLong();     // read file size
        System.out.println("File size: " + size + " bytes");
        
        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
        }
        fileOutputStream.close();
        System.out.println("File saved successfully!");
    }

}
