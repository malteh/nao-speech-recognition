import akka.actor.ActorSystem
import akka.actor.Actor
import com.typesafe.config.ConfigFactory
import akka.actor.ActorRef
import akka.actor.Props
import naogateway.value.NaoMessages._
import naogateway.value.NaoMessages.Conversions._
import naogateway.value.NaoVisionMessages._
import recognition.Converter
import java.io.File
import recorder.HttpRecorder
import recognition.Google
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Await

object RemoteTest extends App {
  val config = ConfigFactory.load()
  val system = ActorSystem("remoting", config.getConfig("remoting").withFallback(config))

  val naoActor = system.actorFor("akka://naogateway@192.168.1.101:2552/user/nila")
  system.actorOf(Props[MyResponseTestActor])

  class MyResponseTestActor extends Actor {
    override def preStart = naoActor ! Connect

    def receive = {
      case (response: ActorRef, noResponse: ActorRef, vision: ActorRef) => {
        trace(response)
        trace(noResponse)
        trace(vision)
        
//        for (m <- Converter.say("ok")) noResponse ! m
        //noResponse ! Call('ALSoundProcessing,'stop,List())
        
        implicit val timeout = Timeout(20 seconds)
        for (i <- 1 to 10) {
          // auf Rückmeldung warten
          val futures_ok = for (m <- Converter.say("ok")) yield response ? m
          for (fu <- futures_ok) Await.result(fu,timeout.duration)
          
          // Aufzeichnung starten
          val f = HttpRecorder.record(3)
          val info = Google.recognize(f)
          // auf Rückmeldung warten
          val futures_command = for (m <- Converter.convert(info.text)) yield response ? m
          for (fu <- futures_command) Await.result(fu,timeout.duration)
          f.delete
        }
        
        //response ! Call('ALBehaviorManager, 'getInstalledBehaviors)
        //response ! Call('ALBehaviorManager, 'runBehavior, List("SitDown"))
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