package recognition

import recorder.HttpRecorder

object Test extends App {
<<<<<<< HEAD
  val f = HttpRecorder.record(3)
  println(f.getAbsoluteFile)
  println(Google.recognize(f))
  f.delete
=======
  val file = "recordings/01.wav"
  HttpRecorder.test2(file)
  println(Google.recognize(file))
>>>>>>> 6c8d4e84e6c5503b3071177ba841d76700324c28
}