package recognition

import naogateway.value.NaoMessages._
import naogateway.value.NaoMessages.Conversions._
import localization._

object Converter extends App {
  val locale = De
  
  
  println(convert("sage test bla"))
  
  def convert(message: String):List[Call] = {
    message.split(" ").toList match {
      case h::t => {
        h match {
          case locale.stand => stand
          case locale.sit => sit
          case locale.say => say(t.mkString(" "))
          case _ => notRecognized(message)
        }
      }
      case Nil => throw new Exception
    }
  }
  
  def stand = Call('ALTextToSpeech, 'say, List(locale.stand))::Call('ALRobotPosture , 'goToPosture, List("StandInit", 0.8f))::Nil
  def sit = Call('ALTextToSpeech, 'say, List(locale.sit))::Nil
  def say(text:String) = Call('ALTextToSpeech, 'say, List(text))::Nil
  def notRecognized(text:String) = say(locale.notRecognized(text))
}