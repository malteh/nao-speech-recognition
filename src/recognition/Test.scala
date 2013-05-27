package recognition

import recorder.HttpRecorder

object Test extends App {
  val file = "recordings/01.wav"
  HttpRecorder.test2(file)
  println(Google.recognize(file))
}