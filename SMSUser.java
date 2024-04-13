package Socket.Socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

// Client1 class to handle client-side socket operations
public class SMSUser {

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    // Constructor to initialize the client and start the chat
    public SMSUser() throws UnknownHostException, IOException {
        // Establishing connection with the server on localhost at port 122
        socket = new Socket("localhost", 123);

        // Initializing input and output streams
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        // Starting the chat
        clientChat();
    }

    // Method to handle client-side chat
    private void clientChat() {
        // Creating a thread to read messages from the server
        ReadWriteHandlerThread readWriteHandlerThread = new ReadWriteHandlerThread(dataInputStream);
        Thread thread = new Thread(readWriteHandlerThread);
        thread.start();

        // Reading input from the user and sending it to the server

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String string = "";
        do {
            try {
                // Reading input from the user
                System.out.print("User: ");
                string = bufferedReader.readLine();
                // Sending the input to the server
                dataOutputStream.writeUTF(string);
                dataOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (!string.equalsIgnoreCase("stop")); // Loop continues until user types "stop"
    }

    // Main method to create a new client instance
    public static void main(String args[]) {
        try {
            new SMSUser();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Runnable class to read messages from the server in a separate thread
class ReadWriteHandlerThread implements Runnable {

    DataInputStream dataInputStream;

    // Constructor to initialize the input stream
    public ReadWriteHandlerThread(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    // Run method to read messages from the server
    @Override
    public void run() {
        String string = "";
        do {
            try {
                // Reading messages from the server
                string = dataInputStream.readUTF();
                // Displaying the received message
                System.out.println(string);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!string.equalsIgnoreCase("stop")); // Loop continues until "stop" message is received
    }
}
