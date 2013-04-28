package recognition

import java.io._
import java.net.HttpURLConnection
import java.net.URL
import helper.Parser._

object Test extends App {

  val ADDRESS = "http://www.google.com/speech-api/v1/recognize?lang=de-de&client=chromium"
  val USER_AGENT = "Mozilla/5.0"
  val CONTENT_TYPE = "audio/x-flac; rate=16000"

  def recognize(filename: String): Info = {
    val url = new URL(ADDRESS)

    val connection:HttpURLConnection = url.openConnection.asInstanceOf[HttpURLConnection]

    connection.setRequestMethod("POST")

    connection.setDoInput(true)
    connection.setDoOutput(true)
    connection.setUseCaches(false)
    
    val file = new File(filename)

    val fileInputStream = new FileInputStream(file)
    
    connection.setRequestProperty("User-Agent", USER_AGENT)
    connection.setRequestProperty("Content-Type", CONTENT_TYPE)
    connection.setRequestProperty("Content-Length", ""+fileInputStream.available())
    

    val maxBufferSize: Int = 10000
    val writer: DataOutputStream = new DataOutputStream(connection.getOutputStream())
    
  
    while (fileInputStream.available() > 0) {
      val min = math.min(fileInputStream.available, maxBufferSize)
      val buffer = new Array[Byte](min)
      fileInputStream.read(buffer, 0, min)
      writer.write(buffer, 0, min)
      println(min)
    }

    writer.flush()
    writer.close()
    
    val reader:BufferedReader = new BufferedReader(new InputStreamReader(
      connection.getInputStream()))

    val buf = new StringBuffer()

    for (line <- reader.readLine()) {
      buf.append(line)
    }


    reader.close()

    val text = extractUtterance(buf.toString())
    val status = extractStatus(buf.toString())
    val confidence = extractConfidence(buf.toString())

    Info(text, status, confidence)
  }

  println(recognize("recordings/hello.flac"))


}