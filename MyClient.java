package Socket.Socket;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.InputStream;

public class MyClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 90);
            System.out.println(socket);
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String string = (String) dataInputStream.readUTF();
            System.out.println(string);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
