import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;


public class Server {
	
	ServerSocket serverSocket;
	//Pipeline pipe;
	//AppSink appSink;
	InputStream is;
	BufferedReader er;
	Process p;
	public LinkedList<byte[]> buffers = new LinkedList<byte[]>();

	public static void main(String[] args) throws Exception {
		new Server().run();
	}
	
	public void start() throws IOException {
		p = Runtime.getRuntime().exec("gst-launch-0.10 tcpserversrc host=192.168.1.108 port=3000 ! oggdemux ! vorbisdec ! audioconvert ! fdsink");
		is = p.getInputStream();
		er = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		new Thread() {
        	public void run() {
				try {
	        		while (true) {
	        			String s = er.readLine();
	        			if (s == null) return;
	        			System.err.println(s);
	        		}
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
		}.start();
	}

	public void exit() {
		try {
			p.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void drop() throws IOException {
		System.out.println("Reading buffer from AppSink");
		byte[] b = new byte[5440];
		int bs = is.read(b);
		if (bs == -1)
			exit();
		//Buffer buffer = appSink.pullBuffer();
		System.out.println("Successfully read buffer from AppSink");
		synchronized(buffers) {
			while (buffers.size() > 60)
				buffers.pop();
			buffers.add(Arrays.copyOf(b, bs));
			//buffers.add(buffer.getByteBuffer());
		}
		System.out.println("Buffer size is now " + buffers.size());
	}

	public void run() throws Exception {
		serverSocket = new ServerSocket(3100);
		new Thread() {
			public void run() {
				try {
					Server.this.start();
				} catch (Exception e) {
					e.printStackTrace();
					exit();
				}
				try {
					while (true)
						drop();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		while (true) {
			final Socket connectionSocket = serverSocket.accept();
            InputStream in = connectionSocket.getInputStream();
            final DataOutputStream out = new DataOutputStream(connectionSocket.getOutputStream());
            final int clientLength = in.read();
            System.out.println("Request to send: " + clientLength);
            new Thread() {
            	public void run() {
            		sendData(out, clientLength);
            		try {
						connectionSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
           	    }
            }.start();
		}
	}
	
	public void sendData(DataOutputStream out, int param) {
		try {
			LinkedList<byte[]> buf = new LinkedList<byte[]>();
			synchronized(buffers) {
				int offset = buffers.size() - param;
				try {
					for (int i = offset; i < buffers.size(); i++)
						buf.push(buffers.get(i));
				} catch (Exception e) {
					System.out.println("Requested more buffers than available.");
				}
			}
			for (byte[] b : buf) {
				/*byte[] bb = new byte[b.remaining()];
				b.get(bb, 0, b.remaining());*/
				out.write(b);
			}
		} catch (Exception e) {
			System.out.println("Connection reset.");
		}
	}
}
