package Socket.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 
 * Server Side...
 * 
 * */

public class NsrGroup {

    static void chatRoom(Socket socket) // read and write operation
    {

        try {

            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            String string = "";
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            while (true) {

                try {
                    string = dataInputStream.readUTF();
                    if (string.equalsIgnoreCase("stop")) {
                        break;
                    }
                    dataOutputStream.writeUTF("Server: " + string);
                    dataOutputStream.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (java.net.SocketException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(123);
            System.out.println("Server is Start and waitng for client request..");
            Socket socket = serverSocket.accept();

            NsrGroup.chatRoom(socket);
            socket.close();
            serverSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
