package org.example.tcpfiletranffering.SocketHandling;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class FTsender {

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    private static final String FILE_TRANSFER = "PEERSEND_FILE";
    
    // Progress callback interface
    public interface ProgressCallback {
        void onProgress(long bytesTransferred, long totalBytes);
        void onComplete();
        void onError(Exception e);
    }

    public static void handleSend(String host, int port, String filePath) {
        handleSend(host, port, filePath, null);
    }
    
    public static void handleSend(String host, int port, String filePath, ProgressCallback callback) {
        try (Socket socket = new Socket(host, port)) {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            // Send file transfer command
            dataOutputStream.writeUTF(FILE_TRANSFER);
            dataOutputStream.flush();
            System.out.println("Sent FILE_TRANSFER command");

            sendFile(filePath, callback);
            
            if (callback != null) {
                callback.onComplete();
            }

            dataInputStream.close();
            dataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onError(e);
            }
        }
    }

    private static void sendFile(String path, ProgressCallback callback) throws Exception{
        int bytes = 0;
        File file = new File(path);
        String fileName = file.getName();
        long fileSize = file.length();
        long totalSent = 0;
        
        System.out.println("Sending file: " + fileName);
        System.out.println("File size: " + fileSize + " bytes");
        FileInputStream fileInputStream = new FileInputStream(file);

        // Send filename first
        dataOutputStream.writeUTF(fileName);
        dataOutputStream.flush();
        System.out.println("Sent filename: " + fileName);
        
        // Send file size
        dataOutputStream.writeLong(fileSize);
        dataOutputStream.flush();
        
        // Send file data in chunks
        byte[] buffer = new byte[4*1024];
        while ((bytes=fileInputStream.read(buffer))!=-1){
            dataOutputStream.write(buffer,0,bytes);
            dataOutputStream.flush();
            totalSent += bytes;
            
            // Report progress
            if (callback != null) {
                callback.onProgress(totalSent, fileSize);
            }
        }
        fileInputStream.close();
        System.out.println("File sent successfully!");
    }

}
