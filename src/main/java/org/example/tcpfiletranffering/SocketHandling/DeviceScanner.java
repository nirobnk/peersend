package org.example.tcpfiletranffering.SocketHandling;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DeviceScanner {
    
    private static final int PORT = 5001;
    private static final int TIMEOUT = 500; // milliseconds
    private static final String SCAN_REQUEST = "PEERSEND_SCAN";
    private static final String SCAN_RESPONSE = "PEERSEND_RECEIVER";
    
    public static class Device {
        private String ipAddress;
        private String hostname;
        
        public Device(String ipAddress, String hostname) {
            this.ipAddress = ipAddress;
            this.hostname = hostname;
        }
        
        public String getIpAddress() {
            return ipAddress;
        }
        
        public String getHostname() {
            return hostname;
        }
        
        @Override
        public String toString() {
            return hostname + " (" + ipAddress + ")";
        }
    }
    
    /**
     * Scans the local network for available devices listening on port 5001
     * @return List of available devices
     */
    public static List<Device> scanNetwork() {
        List<Device> availableDevices = new ArrayList<>();
        
        try {
            // Get local IP address
            InetAddress localHost = InetAddress.getLocalHost();
            String localIp = localHost.getHostAddress();
            
            // Extract subnet (e.g., 192.168.1.x)
            String subnet = localIp.substring(0, localIp.lastIndexOf('.'));
            
            System.out.println("Scanning network: " + subnet + ".0/24 on port " + PORT);
            
            // Use thread pool for faster scanning
            ExecutorService executor = Executors.newFixedThreadPool(50);
            List<Future<Device>> futures = new ArrayList<>();
            
            // Scan all IPs in the subnet (1-254)
            for (int i = 1; i <= 254; i++) {
                final String host = subnet + "." + i;
                
                Callable<Device> task = () -> {
                    try {
                        InetAddress address = InetAddress.getByName(host);
                        
                        // Try to connect to port 5001 and verify it's a PeerSend receiver
                        try (Socket socket = new Socket()) {
                            socket.connect(new InetSocketAddress(host, PORT), TIMEOUT);
                            socket.setSoTimeout(TIMEOUT);
                            
                            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                            DataInputStream in = new DataInputStream(socket.getInputStream());
                            
                            // Send scan request
                            out.writeUTF(SCAN_REQUEST);
                            out.flush();
                            
                            // Wait for response
                            String response = in.readUTF();
                            
                            if (SCAN_RESPONSE.equals(response)) {
                                String hostname = address.getHostName();
                                System.out.println("Found PeerSend device: " + host + " (" + hostname + ")");
                                return new Device(host, hostname);
                            }
                            
                        } catch (Exception e) {
                            // Not a PeerSend receiver or connection failed
                        }
                    } catch (Exception e) {
                        // Host not reachable, skip
                    }
                    return null;
                };
                
                futures.add(executor.submit(task));
            }
            
            // Collect results
            for (Future<Device> future : futures) {
                try {
                    Device device = future.get(1, TimeUnit.SECONDS);
                    if (device != null) {
                        availableDevices.add(device);
                        System.out.println("Found device: " + device);
                    }
                } catch (TimeoutException e) {
                    future.cancel(true);
                } catch (Exception e) {
                    // Ignore
                }
            }
            
            executor.shutdown();
            try {
                executor.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                executor.shutdownNow();
            };
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Scan complete. Found " + availableDevices.size() + " device(s)");
        return availableDevices;
    }
    
    /**
     * Get the local IP address of this machine
     */
    public static String getLocalIpAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            return "Unknown";
        }
    }
}
