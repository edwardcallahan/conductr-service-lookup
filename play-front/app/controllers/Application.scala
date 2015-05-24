package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.Future
import scala.util.control.NonFatal
import play.api.data.Form
import play.api.data.Forms.{ nonEmptyText, mapping }

import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import app.actors.Delivery
import app.actors.Delivery.{ Ack, DeliveryMsg }
import play.api.mvc.{ Action, Controller }

object Application extends Controller {

  val actorSystem = ActorSystem("CharonActorSystem")
  val deliveryActor = actorSystem.actorOf(Props[Delivery], "delivery")
  implicit val timeout = Timeout(5.seconds)

  case class DeceasedMsg(msg: String)
  val DeceasedMsgForm = Form(
    mapping(
      "message" -> nonEmptyText)(DeceasedMsg.apply)(DeceasedMsg.unapply))

  def index = Action.async { implicit request =>
    Future.successful(Ok(views.html.index("Welcome to Charon Shop", DeceasedMsgForm)))
  }

  def sendMsg = Action.async { implicit request =>
    DeceasedMsgForm.bindFromRequest.fold(
      formWithErrors =>
        Future(BadRequest(views.html.index("Send a message. We'll let you know if it gets there!", formWithErrors))),
      formData => {
        deliveryActor.ask(new DeliveryMsg(formData.msg)) map {
          case Ack =>  Redirect(routes.Application.ferryResults("Message Successfully Delivered"))
          case _ =>  Redirect(routes.Application.ferryResults("Unable to deliver message at this time. Try again!"))
          } recover {
            case NonFatal(_) => Redirect(routes.Application.ferryResults("Cannot connect to the backend service. Try again!"))
          }
      })
  }

  def ferryResults(replyMsg: String) = Action.async { implicit request =>
    Future.successful(Ok(views.html.reply(replyMsg)))
  }
}
