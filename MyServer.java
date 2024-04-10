package Socket.Socket;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args)  {
        try {
            ServerSocket serverSocket = new ServerSocket(90);
            Socket socket = serverSocket.accept();
            serverSocket.close();
            System.out.println(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
