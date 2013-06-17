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
	// Pipeline pipe;
	// AppSink appSink;
	InputStream is;
	BufferedReader er;
	Process p;
	public LinkedList<byte[]> buffers = new LinkedList<byte[]>();

	public static byte[] header = new byte[] { 'R', 'I', 'F', 'F', 0x30, 0x3a,
			0x05, 0x00, 'W', 'A', 'V', 'E', 'f', 'm', 't', ' ', 0x10, 0x00,
			0x00, 0x00, 0x01, 0x00, 0x01, 0x00, (byte) 0x80, 0x3e, 0x00, 0x00,
			0x00, 0x7d, 0x00, 0x00, 0x20, 0x00, 0x10, 0x00, 'd', 'a', 't', 'a',
			0x0c, 0x3a, 0x05, 0x00 };

	public static byte[] header = new byte[] {
		'R', 'I', 'F', 'F', 0x30, 0x3a, 0x05, 0x00, 'W', 'A', 'V', 'E', 'f', 'm', 't', ' ',
		0x10,0x00,0x00,0x00,0x01,0x00,0x01,0x00, (byte)0x80, 0x3e, 0x00, 0x00, 0x00, 0x7d, 0x00, 0x00,
		0x20,0x00,0x10,0x00, 'd', 'a', 't', 'a', 0x0c, 0x3a, 0x05, 0x00
	};
	
	public static void main(String[] args) throws Exception {
		new Server().run();
	}

	public void start() throws IOException {
<<<<<<< HEAD
		p = Runtime
				.getRuntime()
				.exec("gst-launch-0.10 tcpserversrc host=192.168.1.108 port=3000 ! oggdemux ! vorbisdec ! audioconvert ! fdsink");
=======
		p = Runtime.getRuntime().exec("gst-launch-0.10 tcpserversrc host=192.168.1.108 port=3000 ! oggdemux ! vorbisdec ! audioconvert ! fdsink");
>>>>>>> dcd5ff84a542c6641b51312d6fa1f888d734ab99
		is = p.getInputStream();
		er = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		new Thread() {
			public void run() {
				try {
					while (true) {
						String s = er.readLine();
						if (s == null)
							return;
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
<<<<<<< HEAD
		// System.out.println("Reading buffer from AppSink");
=======
		//System.out.println("Reading buffer from AppSink");
>>>>>>> dcd5ff84a542c6641b51312d6fa1f888d734ab99
		byte[] b = new byte[5440];
		int bs = is.read(b);
		if (bs == -1)
			exit();
<<<<<<< HEAD
		// Buffer buffer = appSink.pullBuffer();
		// System.out.println("Successfully read buffer from AppSink");
		synchronized (buffers) {
=======
		//Buffer buffer = appSink.pullBuffer();
		//System.out.println("Successfully read buffer from AppSink");
		synchronized(buffers) {
>>>>>>> dcd5ff84a542c6641b51312d6fa1f888d734ab99
			while (buffers.size() > 60)
				buffers.pop();
			buffers.add(Arrays.copyOf(b, bs));
			// buffers.add(buffer.getByteBuffer());
		}
<<<<<<< HEAD
		System.out.println("Buffer size is now " + buffers.size()
				+ ", size of last buffer: " + bs);
=======
		System.out.println("Buffer size is now " + buffers.size() + ", size of last buffer: " + bs);
>>>>>>> dcd5ff84a542c6641b51312d6fa1f888d734ab99
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
			final DataOutputStream out = new DataOutputStream(
					connectionSocket.getOutputStream());
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
<<<<<<< HEAD
			LinkedList<byte[]> buf = new LinkedList<byte[]>();
			buf.add(header);
			byte[] sine = new byte[] { 0x00, 0x00, 0x11, 0x11, 0x22, 0x22,
					0x33, 0x33, 0x44, 0x44, 0x55, 0x55, 0x66, 0x66, 0x77, 0x77,
					(byte) 0x88, (byte) 0x88, (byte) 0x99, (byte) 0x99,
					(byte) 0xaa, (byte) 0xaa, (byte) 0xbb, (byte) 0xbb,
					(byte) 0xcc, (byte) 0xcc, (byte) 0xdd, (byte) 0xdd,
					(byte) 0xee, (byte) 0xee, (byte) 0xff, (byte) 0xff };
			for (int i = 0; i < 1000; i++) {
				buf.add(sine);
			}
			synchronized (buffers) {
=======
			LinkedList<byte[]> buf = new LinkedList<>();
			buf.add(header);
			byte[] sine = new byte[] {0x00,0x00,0x11,0x11,0x22,0x22,0x33,0x33,0x44,0x44,0x55,0x55,0x66,0x66,0x77,0x77,(byte)0x88,(byte)0x88,(byte)0x99,(byte)0x99,(byte)0xaa,(byte)0xaa,(byte)0xbb,(byte)0xbb,(byte)0xcc,(byte)0xcc,(byte)0xdd,(byte)0xdd,(byte)0xee,(byte)0xee,(byte)0xff,(byte)0xff};
			for (int i = 0; i < 1000; i++) {
				buf.add(sine);
			}
			synchronized(buffers) {
>>>>>>> dcd5ff84a542c6641b51312d6fa1f888d734ab99
				int offset = buffers.size() - param;
				try {
					for (int i = offset; i < buffers.size(); i++)
						buf.add(buffers.get(i));
				} catch (Exception e) {
					System.out
							.println("Requested more buffers than available.");
				}
			}
			for (byte[] b : buf) {
				/*
				 * byte[] bb = new byte[b.remaining()]; b.get(bb, 0,
				 * b.remaining());
				 */
				out.write(b);
			}
		} catch (Exception e) {
			System.out.println("Connection reset.");
		}
	}
}
