package Socket.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * 
 * Client side
 * 
 * */
public class EndUser {
    public static void main(String[] args) {
        try {
            EndUser.chat();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void chat() {
        try {
            Socket socket = new Socket("localhost", 123);
            InputStream inputStream = socket.getInputStream();
            Scanner scanner = new Scanner(System.in);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String str = "";

            try {
                while (true) {
                    System.out.print("User: ");
                    str = scanner.nextLine();
                    dataOutputStream.writeUTF(str);

                    if (str.equalsIgnoreCase("stop")) {
                        break;
                    }

                    String response = dataInputStream.readUTF();
                    System.out.println("Server: " + response);
                }
            } catch (EOFException e) {
                // Handle EOFException
                System.out.println("Connection closed by server.");
            } finally {
                // Close resources
                scanner.close();
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
