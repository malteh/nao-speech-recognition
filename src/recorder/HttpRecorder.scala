package recorder

import java.net.URL
import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.AudioFormat
import java.io.File
import javax.sound.sampled.AudioFileFormat
import java.io.FileOutputStream
import java.io.FileInputStream
import java.io.InputStream
import javax.sound.sampled.AudioInputStream
import java.io.ByteArrayInputStream
import java.io.DataOutputStream

object HttpRecorder {

  val url = new URL("http://192.168.0.100:8080/audio.wav")

  def main(args: Array[String]): Unit = {
    test2

  }

  def test2() = {
    val conn = url.openConnection();
    val is = conn.getInputStream();

    val tmp = new File("recordings/tmp.wav")

    val outstream = new FileOutputStream(tmp);
    val buffer = new Array[Byte](4096)
    var len: Int = 0
    val t = System.currentTimeMillis()
    while (len >= 0 && System.currentTimeMillis() - t <= 5000) {
      len = is.read(buffer)
      outstream.write(buffer, 0, len)
    }
    outstream.close();
    val inFile = AudioSystem.getAudioInputStream(tmp)
    AudioSystem.write(inFile, AudioFileFormat.Type.WAVE, new File("recordings/01.wav"));

  }

  def getAudioFormat: AudioFormat = {
    val sampleRate = 44100.0F;
    // 8000,11025,16000,22050,44100
    val sampleSizeInBits = 16;
    // 8,16
    val channels = 1;
    // 1,2
    val signed = true;
    // true,false
    val bigEndian = false;
    // true,false
    new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
  } // end getAudioFormat

  def test() = {

    val is = url.openStream()

    val bis = new BufferedInputStream(is, 1024 * 1024);
    val sound = AudioSystem.getAudioInputStream(bis);
    val pcm = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, sound);
    val ulaw = AudioSystem.getAudioInputStream(AudioFormat.Encoding.ULAW, pcm);

    //val tempFile = File.createTempFile("wav", "tmp");
    val tempFile = new File("recordings/tmp.wav")
    AudioSystem.write(ulaw, AudioFileFormat.Type.WAVE, tempFile);

    sound.close()
    bis.close()
    is.close()
  }

  /*





return bytes;
   * 
   * */

}