package app.actors

import scala.concurrent.duration.DurationInt

import com.typesafe.conductr.bundlelib.scala.LocationService

import akka.actor.{Actor, ActorLogging}
import akka.pattern.pipe
import akka.util.Timeout
import play.api.Play.current
import play.api.libs.ws.WS

object Messages {
  sealed abstract trait Message

  case class DeliveryMsg(msg: String) extends Message

  case object Ack extends Message

  case object Nack extends Message
}

class Delivery extends Actor with ActorLogging {  
  import Messages._
  implicit val timeout = Timeout(5.seconds)
  implicit val executionContext = context.dispatcher
  val ferry = LocationService.getLookupUrl("/transport", "http://127.0.0.1:9666/transport")

  def receive = {
    case delivery: DeliveryMsg =>
      val msg = delivery.msg
      WS.url(s"$ferry/transport/$msg").withFollowRedirects(follow = true).get
        .map { response =>
          response.status match {
            case 200 => Ack
            case _   => Nack
          }
        }
        .pipeTo(sender())
  }

}

