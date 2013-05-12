package recognition

import naogateway.value.NaoMessages._
import naogateway.value.NaoMessages.Conversions._
import localization._

object Converter {

  /**
   * Erweiterungen werden in der Klasse localization.Locale vorgenommen
   */
  val locale: Locale = De

  /**
   * Wandelt einen beliebigen String in eine Liste von Calls um
   * @param message Die Nachricht, die umgewandelt werden soll
   */
  def convert(message: String): List[Call] = {
    message.split(" ").toList match {
      case h :: t => {
        h match {
          case locale.stand => stand
          case locale.sit => sit
          case locale.say => say(t.mkString(" "))
          case locale.randomEyes => randomEyes
          case locale.rotateEyes => rotateEyes
          case locale.loose => loose
          case locale.stiff => stiff
          case _ => notRecognized(message)
        }
      }
      case Nil => throw new Exception
    }
  }

  /**
   * Erzeugt einen Call um den Nao aufstehen zu lassen
   *  bool ALRobotPosture::goToPosture(const std::string postureName, const float speed)
   *  http://www.aldebaran-robotics.com/documentation/naoqi/motion/alrobotposture.html#term-predefined-postures
   */
  def stand = Call('ALTextToSpeech, 'say, List(locale.stand)) :: Call('ALRobotPosture, 'goToPosture, List("Stand", 0.8f)) :: Nil

  /** Erzeugt einen Call um den Nao hinsetzen zu lassen */
  def sit = Call('ALTextToSpeech, 'say, List(locale.sit)) :: Call('ALRobotPosture, 'goToPosture, List("Sit", 0.8f)) :: Nil

  /**
   * Erzeugt einen Call um den Nao einen Text sagen zu lassen
   *  void ALTextToSpeechProxy::say(const std::string& stringToSay)
   *  @param text Der Text den der Nao sagen soll
   */
  def say(text: String) = Call('ALTextToSpeech, 'say, List(text)) :: Nil

  /**
   * Erzeugt einen Call um die Augen des Nao für 3 Sekunen in einer zufälligen Farbe aufleuchten zu lassen
   *  void ALLeds::randomEyes(const float& duration)
   */
  def randomEyes = Call('ALLeds, 'randomEyes, List(3.0f)) :: Nil

  /**
   * Erzeugt einen Call um die Augen des Nao für 3 Sekunen in rot rotieren zu lassen
   *  void ALLeds::rotateEyes(const int& rgb, const float& timeForRotation, const float& totalDuration)
   */
  def rotateEyes = Call('ALLeds, 'rotateEyes, List(0x00FF0000, 1.0f, 3.0f)) :: Nil

  /**
   * Erzeugt einen Call um den Körper des Nao locker zu schalten
   */
  def loose = Call('ALMotion, 'setStiffnesses, List("Body", 0.0f)) :: Nil

  /**
   * Erzeugt einen Call um den Körper des Nao steif zu schalten
   */
  def stiff = Call('ALMotion, 'setStiffnesses, List("Body", 1.0f)) :: Nil

  /**
   * Erzeugt einen Call um mitzuteilen, dass der Befehl nicht erkannt wurde
   * @param text Der nicht erkannte Text
   */
  def notRecognized(text: String) = say(locale.notRecognized(text))

  /**
   * Beispielaufruf
   */
  def main(args: Array[String]) = {
    println(convert("sage hallo"))
  }
}