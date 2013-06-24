package recorder

import java.io.File
import java.net.Socket
import java.io.DataOutputStream
import java.io.RandomAccessFile
import java.io.FileOutputStream
import javaFlacEncoder.FLAC_FileEncoder

object TcpRecorder {

  def record(duration: Int): File = {
    val tmp = new File("recordings/tmp.wav")
    val hostname = "192.168.1.108"
    val port = 3100
    val buffer = new Array[Byte](5440)

    val clientSocket = new Socket(hostname, port)
    val out = new DataOutputStream(clientSocket.getOutputStream)
    val in = clientSocket.getInputStream

    out.writeByte(duration)
    out.flush

    val outstream = new FileOutputStream(tmp)

    var len = 0
    len = in.read(buffer)
    while (len >= 0) {

      outstream.write(buffer, 0, len)
      //System.out.write(buffer, 0, len);
      len = in.read(buffer)
    }

    clientSocket.close
    outstream.close

    val flacEncoder = new FLAC_FileEncoder()

    //     mieser Hack -->
    //        val raf = new RandomAccessFile(tmp, "rw");
    //        raf.seek(0); // Go to byte at offset position 5.
    //        raf.writeBytes("RIFF")
    //        raf.write(0xb0)
    //        raf.writeBytes("R")
    //        raf.write(0x03)
    //        raf.write(0x00)
    //        raf.writeBytes("WAVEfmt ")
    //        raf.write(Array[Byte](0x10, 0x00, 0x00, 0x00, 0x01, 0x00, 0x01, 0x00))
    //        raf.writeBytes("D")
    //        raf.write(0xac)
    //        raf.write(Array[Byte](0x00, 0x00))
    //        raf.write(0x88)
    //        raf.writeBytes("X")
    //        raf.write(Array[Byte](0x01, 0x00, 0x02, 0x00, 0x10, 0x00))
    //        raf.writeBytes("data")
    //        raf.write(0x8c)
    //        raf.writeBytes("R")
    //        raf.write(Array[Byte](0x03, 0x00))
    //        raf.close // Flush/save changes and close resource.
    //     <-- mieser Hack
    
    flacEncoder.encode(tmp, new File("recordings/tmp.flac"))
    
    tmp
  }

  def main(args: Array[String]): Unit = {
    record(600)
  }

}