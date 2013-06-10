package recognition

import naogateway.value.NaoMessages._
import naogateway.value.NaoMessages.Conversions._

object CommandMap extends App {

  def map(textToMap: String) = {

    val command = textToMap.split(" ").toList match {
      case h :: Nil => h
      case h :: t => (h, t.mkString(" "))
    }

    command match {
      case "aufstehen" => Call('ALRobotPosture, 'goToPosture, List("Stand", 1.0f)) :: Nil
      case ("sage", textToSay: String) => Call('ALTextToSpeech, 'say, List(textToSay)) :: Nil
      case "hinsetzen" => Call('ALRobotPosture, 'goToPosture, List("Sit", 1.0f)) :: Nil
      case "augen zufällig" => Call('ALLeds, 'randomEyes, List(3.0f)) :: Nil
      case "augen rotieren" => Call('ALLeds, 'rotateEyes, List(0x00FF0000, 1.0f, 3.0f)) :: Nil
      case "schlafen" => Call('ALTextToSpeech, 'say, List("immer locker bleiben")) :: Call('ALMotion, 'setStiffnesses, List("Body", 0.0f)) :: Nil
      case "unbeweglich" => Call('ALMotion, 'setStiffnesses, List("Body", 1.0f)) :: Nil
      case "terminieren" => Call('ALRobotPosture, 'goToPosture, List("Stand", 0.8f)) :: Call('ALLeds, 'rotateEyes, List(0x00FF0000, 0.1f, 0.1f)) :: Call('ALTextToSpeech, 'say, List("terminieren")) :: Nil
      case (_, text: String) => Call('ALTextToSpeech, 'say, List("ich habe " + text + " nicht verstanden")) :: Nil
      case text: String => Call('ALTextToSpeech, 'say, List("ich habe " + text + " nicht verstanden")) :: Nil
    }
  }

  println(map("locker"))
  println(map("aufstehen"))
  println(map("sage hallo"))
}