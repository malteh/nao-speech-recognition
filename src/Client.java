import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class Client {
	public static void main(String argv[]) throws Exception {
		int duration = 10;
		String hostname = "192.168.1.108";
		int port = 3100;
		byte[] b = new byte[5440];

        Socket clientSocket = new Socket(hostname, port);
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        InputStream in = clientSocket.getInputStream();

        out.writeByte(duration);
        out.flush();
        int count;
        while ((count = in.read(b)) >= 0) {
            System.out.write(b, 0, count);
        }
        
        clientSocket.close();
    }
}

