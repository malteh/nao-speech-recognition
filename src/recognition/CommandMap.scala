package recognition

import naogateway.value.NaoMessages._
import naogateway.value.NaoMessages.Conversions._

object CommandMap extends App {
  val map = Map(
    /**
     * Erzeugt einen Call um den Nao aufstehen zu lassen
     *  bool ALRobotPosture::goToPosture(const std::string postureName, const float speed)
     *  http://www.aldebaran-robotics.com/documentation/naoqi/motion/alrobotposture.html#term-predefined-postures
     */
    "aufstehen" -> (Call('ALTextToSpeech, 'say, List("aufstehen")) :: Call('ALRobotPosture, 'goToPosture, List("Stand", 1.0f)) :: Nil),
    /** Erzeugt einen Call um den Nao hinsetzen zu lassen */
    "hinsetzen" -> (Call('ALTextToSpeech, 'say, List("hinsetzen")) :: Call('ALRobotPosture, 'goToPosture, List("Sit", 1.0f)) :: Nil),
    
    /**
   * Erzeugt einen Call um den Nao einen Text sagen zu lassen
   *  void ALTextToSpeechProxy::say(const std::string& stringToSay)
   *  @param text Der Text den der Nao sagen soll
   */
  "sage" -> ((p:String) => (Call('ALTextToSpeech, 'say, List(""+p)) :: Nil)))
  
  println(map("aufstehen"))
  println(map("sage"))
}