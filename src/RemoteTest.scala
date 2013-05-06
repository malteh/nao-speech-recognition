import akka.actor.ActorSystem
import akka.actor.Actor
import com.typesafe.config.ConfigFactory
import akka.actor.ActorRef
import akka.actor.Props
import naogateway.value.NaoMessages._
import naogateway.value.NaoMessages.Conversions._
import naogateway.value.NaoVisionMessages._
import recognition.Converter

object RemoteTest extends App{  
  val config = ConfigFactory.load()
  val system = ActorSystem("remoting",config.getConfig("remoting").withFallback(config))
 
  val naoActor = system.actorFor("akka://naogateway@192.168.1.100:2550/user/hanna")
  system.actorOf(Props[MyResponseTestActor])
  
  class MyResponseTestActor extends Actor  {
    override def preStart = naoActor ! Connect

    def receive = {
      case (response: ActorRef, noResponse: ActorRef, vision: ActorRef) => {
        trace(response)
        trace(noResponse)
        trace(vision)
//        response ! Call('ALTextToSpeech, 'getVolume)
//        xresponse ! Call('ALTextToSpeech, 'getAvailableVoices)
//        noResponse ! Call('ALTextToSpeech, 'setVoice, List("Sarah22Enhanced"))
        
        //while(true)response ! Call('ALTextToSpeech, 'getVoices)
//        noResponse ! Call('ALTextToSpeech, 'say, List("aufstehen"))
//        //ALRobotPostureProxy.goToPosture("StandInit", 0.5f)
//        noResponse ! Call('ALRobotPosture , 'goToPosture, List("StandInit", 0.8f))
//        noResponse ! Call('ALTextToSpeech, 'say, List("hinsetzen"))
//        noResponse ! Call('ALRobotPosture , 'goToPosture, List("Sit", 0.8f))
        //
        for (m <- Converter.convert("sage bleibe locker")) noResponse ! m
        
        noResponse ! Call('ALMotion , 'setStiffnesses, List("Body",0.0f))
        
//        vision ! VisionCall(Resolutions.k4VGA,ColorSpaces.kBGR,Frames._20)
//        vision ! RawVisionCall(Resolutions.k4VGA,ColorSpaces.kBGR,Frames._20)
      }    
      case x => trace(x)
    }
    
    def trace(a: Any) = log.info(a.toString)
    def error(a: Any) = log.warning(a.toString)
    def wrongMessage(a: Any, state: String) = log.warning("wrong message: " + a + " in " + state)
    import akka.event.Logging
    val log = Logging(context.system, this)
    
  }
  Thread.sleep(2000)
system.shutdown
}