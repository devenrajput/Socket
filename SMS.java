package Socket.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class SMS {
    @SuppressWarnings("rawtypes")
    ArrayList al = new ArrayList(); // List to store client sockets
    ServerSocket serverSocket;
    Socket socket;

    // Constructor to initialize the server
    @SuppressWarnings("unchecked")
    SMS() throws IOException {
        serverSocket = new ServerSocket(123); // Create server socket on port 122
        System.out.println("Server is started and waiting for client request...");
        while (true) {
            socket = serverSocket.accept(); // Accept client connection
            al.add(socket); // Add client socket to the list
            Runnable runnable = new MyThread(socket, al); // Create thread for client communication
            Thread thread = new Thread(runnable); // Start thread
            thread.start();
        }
    }

    public static void main(String args[]) {
        try {
            new SMS(); // Create new SMS object to start server
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MyThread implements Runnable {
    @SuppressWarnings("rawtypes")
    ArrayList arrayList;
    Socket socket;
    DataInputStream dataInputStream;

    // Constructor to initialize thread with client socket and list
    @SuppressWarnings("rawtypes")
    public MyThread(Socket socket, ArrayList arrayList) {
        this.arrayList = arrayList;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            String string = "";
            do {
                string = dataInputStream.readUTF(); // Read message from client
                if (!string.equals("stop")) {
                    tellEveryOne(string); // Send message to all clients
                } else {
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF(string); // Send "stop" message back to client
                    dataOutputStream.flush();
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to send message to all clients
    @SuppressWarnings("rawtypes")
    private final void tellEveryOne(String string) {
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            Socket socket = (Socket) iterator.next();
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(string); // Send message to client
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
