package Socket.Socket;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressDemo {

	public static void main(String[] args) {
	
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			System.out.println(inetAddress); 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
