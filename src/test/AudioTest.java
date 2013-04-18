package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

public class AudioTest {

	public static void main(String[] args) {
		// test();
		try {
			mixerInfo();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void test() {
		float sampleRate = 8000;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
				channels, signed, bigEndian);

		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		TargetDataLine line = null;
		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
		byte buffer[] = new byte[bufferSize];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int x = 0;
		while (x < 100) {
			int count = line.read(buffer, 0, buffer.length);
			if (count > 0) {
				out.write(buffer, 0, count);
			}
			x++;
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void mixerInfo() throws LineUnavailableException {
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		for (Mixer.Info info : mixerInfos) {
			Mixer m = AudioSystem.getMixer(info);
			Line.Info[] lineInfos = m.getSourceLineInfo();
			for (Line.Info lineInfo : lineInfos) {
				System.out.println(info.getName() + "---" + lineInfo);
				Line line = m.getLine(lineInfo);
				System.out.println("\t-----" + line);
			}
			lineInfos = m.getTargetLineInfo();
			for (Line.Info lineInfo : lineInfos) {
				System.out.println(m + "---" + lineInfo);
				Line line = m.getLine(lineInfo);
				System.out.println("\t-----" + line);

			}

		}
	}

}
