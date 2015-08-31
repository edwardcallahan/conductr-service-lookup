package app.actors

import scala.concurrent.duration.DurationInt
import akka.actor.{ Actor, ActorLogging, Props }
import akka.pattern.pipe
import akka.util.Timeout
import play.api.Play.current
import play.api.libs.ws.WS
import java.net.{URLEncoder}
import com.typesafe.conductr.bundlelib.play.LocationService
import com.typesafe.conductr.bundlelib.scala.URL

object Delivery {
  sealed trait Message
  case class DeliveryMsg(msg: String) extends Message
  case object Ack extends Message
  case object Nack extends Message

  def props: Props = Props(new Delivery)
}

class Delivery extends Actor with ActorLogging {
  import Delivery._
  implicit val timeout = Timeout(5.seconds)
  implicit val executionContext = context.dispatcher
  val ferry = LocationService.getLookupUrl("/ferry", URL("http://127.0.0.1:9666"))

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

