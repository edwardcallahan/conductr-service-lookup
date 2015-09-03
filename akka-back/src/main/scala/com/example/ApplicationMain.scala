package com.example

import scala.concurrent.{ ExecutionContext, Future }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import com.typesafe.conductr.bundlelib.akka.{ ConnectionContext, StatusService }
import com.typesafe.config.ConfigFactory
import akka.actor.{ ActorRef, ActorSystem }
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.ToResponseMarshallable.apply
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directive.{ addByNameNullaryApply, addDirectiveApply }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout

object Status {
  case class Success()
  case class Error(error: String)
}

sealed abstract trait Message
case class TransportMessage(msg: String) extends Message

object ApplicationMain extends App {

  implicit val system = ActorSystem("FerryActorSystem")
  implicit val cc = ConnectionContext()
  import cc.actorMaterializer
  implicit val timeout = Timeout(5.seconds)
  val boatActor = system.actorOf(BoatActor.props, "boatActor")

  val config = ConfigFactory.load()
  private def ip = config.getString("ferry-service.ip")
  private def port = config.getInt("ferry-service.port")

  Http(system).bindAndHandle(routeFerry(boatActor), ip, port)
  StatusService.signalStartedOrExit

  private def routeFerry(
    ferry: ActorRef)(implicit ec: ExecutionContext): Route =
    pathPrefix("transport") {
      path("message" / Segment) { msg =>
        get {
            complete {
              ferry ask TransportMessage(msg) map {
                case Status.Success =>
                  Future.successful(StatusCodes.OK)
                case Status.Error(_) =>
                  Future.failed(new Throwable(StatusCodes.BadRequest.value))
              }
            }
        }
      }
    }

}