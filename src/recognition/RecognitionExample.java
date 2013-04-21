package recognition;

import helper.Parser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecognitionExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(recognize("recordings/hello.flac"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static final String ADDRESS = "http://www.google.com/speech-api/v1/recognize?lang=de-de&client=chromium";
	public static final String USER_AGENT = "Mozilla/5.0";
	public static final String CONTENT_TYPE = "audio/x-flac; rate=16000";

	public static Info recognize(String filename) throws Exception {
		URL url = new URL(ADDRESS);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);

		connection.setRequestProperty("User-Agent", USER_AGENT);
		connection.setRequestProperty("Content-Type", CONTENT_TYPE);

		DataOutputStream writer = new DataOutputStream(
				connection.getOutputStream());
		int maxBufferSize = 1000;

		File file = new File(filename);
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
		writer.close();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));

		StringBuffer buf = new StringBuffer();

		for (String line; (line = reader.readLine()) != null;) {
			//System.out.println(line);
			buf.append(line);
		}

		reader.close();

		String text = Parser.extractUtterance(buf.toString());
		Double confidence = Parser.extractConfidence(buf.toString());
		int status = Parser.extractStatus(buf.toString());

		return new Info(text, status, confidence);
	}
}
