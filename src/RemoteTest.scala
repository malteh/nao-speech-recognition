import akka.actor.ActorSystem
import akka.actor.Actor
import com.typesafe.config.ConfigFactory
import akka.actor.ActorRef
import akka.actor.Props
import naogateway.value.NaoMessages._
import naogateway.value.NaoMessages.Conversions._
import naogateway.value.NaoVisionMessages._
import java.io.File
import recorder.HttpRecorder
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Await
import recognition.CommandMap
import recognition.Recognizer

object RemoteTest extends App {
  val config = ConfigFactory.load()
  val system = ActorSystem("remoting", config.getConfig("remoting").withFallback(config))

  val naoActor = system.actorFor("akka://naogateway@192.168.1.100:2550/user/hanna")
  system.actorOf(Props[MyResponseTestActor])

  class MyResponseTestActor extends Actor {
    override def preStart = naoActor ! Connect

    def receive = {
      case (response: ActorRef, noResponse: ActorRef, vision: ActorRef) => {
        trace(response)
        trace(noResponse)
        trace(vision)

        implicit val timeout = Timeout(20 seconds)
        for (i <- 1 to 10) {
          // auf Rueckmeldung warten
          for (m <- CommandMap.map("sage ok")) {
            val f = response ? m
            Await.result(f, timeout.duration)
          }

          // Aufzeichnung starten
          val f = HttpRecorder.record(3)
          val info = Recognizer.recognize(f)

          // auf Rueckmeldung warten
          println(info.text)
          val futures_command = for (m <- CommandMap.map(info.text)) yield response ? m
          for (m <- CommandMap.map(info.text)) {
            val f = response ? m
            Await.result(f, timeout.duration)
          }

          f.delete
        }
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