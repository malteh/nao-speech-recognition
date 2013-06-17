package recognition

import recorder.HttpRecorder

object Test extends App {
  val f = HttpRecorder.record(3)
  println(f.getAbsoluteFile)
  println(Recognizer.recognize(f))
  f.delete
}

