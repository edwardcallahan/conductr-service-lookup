package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import app.actors.Delivery
import app.actors.Messages.Ack
import app.actors.Messages.DeliveryMsg
import play.api.mvc.Action
import play.api.mvc.Controller

object Application extends Controller {

  val actorSystem = ActorSystem("CharonActorSystem")
  val deliveryActor = actorSystem.actorOf(Props[Delivery], "delivery")
  implicit val timeout = Timeout(5.seconds)
  
  def index = Action.async { request =>
    deliveryActor.ask(new DeliveryMsg("Greetings")) map {
      case Ack => Ok(s"""Message Delivered""")
      case _   => InternalServerError("Unable to deliver message")
    }
  }

}
