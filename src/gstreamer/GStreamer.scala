package gstreamer

import akka.actor.ActorSystem
import akka.actor.Actor
import com.typesafe.config.ConfigFactory
import akka.actor.ActorRef
import akka.actor.Props
import naogateway.value.NaoMessages._
import naogateway.value.NaoMessages.Conversions._
import naogateway.value.NaoVisionMessages._
import recognition.Converter
import akka.event.Logging
import org.gstreamer.Gst
import org.gstreamer.elements.PlayBin
import org.gstreamer.ElementFactory
import org.gstreamer.State
import org.gstreamer.Pipeline
import org.gstreamer.Caps
import org.gstreamer.swing.VideoComponent

object GStreamer extends App {
  // val config = ConfigFactory.load()
  //val system = ActorSystem("remoting", config.getConfig("remoting").withFallback(config))

  //val naoActor = system.actorFor("akka://naogateway@192.168.1.100:2550/user/hanna")
  //system.actorOf(Props[GStreamActor])
  val gst = Gst.init("VideoPlayer",args)
  val pipe = new Pipeline();
  val src = ElementFactory.make("souphttpsrc","souphttpsrc")
  src.set("location","http://192.168.22:8080/video")
  val jpg = ElementFactory.make("jpegdec", "jpegdec")
  
  //"http://192.168.178.22:8080/video");
  val sink = ElementFactory.make("autovideosink", "autovideosink");
        pipe.addMany(src, jpg, sink);
        src.link(sink);
        pipe.setState(State.PLAYING);
        Gst.main();
        pipe.setState(State.NULL);
  
      
          def trace(a: Any) = System.out.println(a.toString)
          
      
}