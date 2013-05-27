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
import java.io.RandomAccessFile

object HttpRecorder {

  val url = new URL("http://192.168.0.100:8080/audio.wav")

  def main(args: Array[String]): Unit = {
    test2("recordings/tmp.wav")

  }

  def test2(file: String) = {
    val tmp = new File(file)

    val conn = url.openConnection
    val is = conn.getInputStream
    val outstream = new FileOutputStream(tmp)
    val buffer = new Array[Byte](4096)
    var len: Int = 0
    val t = System.currentTimeMillis
    while (len >= 0 && System.currentTimeMillis - t <= 5000) {
      len = is.read(buffer)
      outstream.write(buffer, 0, len)
    }
    outstream.close
    is.close

    val raf = new RandomAccessFile(tmp, "rw");
    raf.seek(0); // Go to byte at offset position 5.
    raf.writeBytes("RIFF")
    raf.write(0xb0)
    raf.writeBytes("R")
    raf.write(0x03)
    raf.write(0x00)
    raf.writeBytes("WAVEfmt ")
    raf.write(Array[Byte](0x10, 0x00, 0x00, 0x00, 0x01, 0x00, 0x01, 0x00))
    raf.writeBytes("D")
    raf.write(0xac)
    raf.write(Array[Byte](0x00, 0x00))
    raf.write(0x88)
    raf.writeBytes("X")
    raf.write(Array[Byte](0x01, 0x00, 0x02, 0x00, 0x10, 0x00))
    raf.writeBytes("data")
    raf.write(0x8c)
    raf.writeBytes("R")
    raf.write(Array[Byte](0x03, 0x00))
    raf.close(); // Flush/save changes and close resource.
  }
}