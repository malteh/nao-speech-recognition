package test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {

	public static void test() throws Exception {

		URL url = new URL(
				"https://www.google.com/speech-api/v1/recognize?xjerr=1&client=chromium&lang=de");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Type", "audio/x-flac; rate=16000");
		
		// connection.setRequestProperty("Content-Length",
		// String.valueOf(body.length()));

		// OutputStreamWriter writer = new
		// OutputStreamWriter(connection.getOutputStream());
		DataOutputStream writer = new DataOutputStream(
				connection.getOutputStream());
		int maxBufferSize = 1000;
		File file = new File("hello.flac");
		
		FileInputStream fileInputStream = new FileInputStream(file);
		int bytesAvailable = fileInputStream.available();
		byte[] buffer = new byte[bytesAvailable];

		int bytesRead = fileInputStream.read(buffer, 0, bytesAvailable);
		while (bytesRead > 0) {
			writer.write(buffer, 0, bytesAvailable);
			bytesAvailable = fileInputStream.available();
			bytesAvailable = Math.min(bytesAvailable, maxBufferSize);
			bytesRead = fileInputStream.read(buffer, 0, bytesAvailable);
		}

		writer.flush();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));

		for (String line; (line = reader.readLine()) != null;) {
			System.out.println(line);
		}

		writer.close();
		reader.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			test();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
