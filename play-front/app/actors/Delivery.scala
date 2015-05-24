package app.actors

import scala.concurrent.duration.DurationInt

import com.typesafe.conductr.bundlelib.scala.LocationService

import akka.actor.{ Actor, ActorLogging, Props }
import akka.pattern.pipe
import akka.util.Timeout
import play.api.Play.current
import play.api.libs.ws.WS
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

object Delivery {
  def props(): Props = Props(new Delivery)
  sealed abstract trait Message
  case class DeliveryMsg(msg: String) extends Message
  case object Ack extends Message
  case object Nack extends Message
}

class Delivery extends Actor with ActorLogging {
  import Delivery._
  implicit val timeout = Timeout(5.seconds)
  implicit val executionContext = context.dispatcher
  val ferry = LocationService.getLookupUrl("/ferry", "http://127.0.0.1:9666")

  def receive = {
    case delivery: DeliveryMsg =>
      val msg = URLEncoder.encode(delivery.msg, "UTF-8")
      WS.url(s"$ferry/transport/message/$msg").withFollowRedirects(follow = true).get
        .map { response =>
          response.status match {
            case 200 => Ack
            case _   => Nack
          }
        }
        .pipeTo(sender())
  }

}

