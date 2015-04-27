package com.example

import scala.concurrent.{ ExecutionContext, Future }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import com.typesafe.conductr.bundlelib.akka.{ ConnectionContext, StatusService }
import com.typesafe.config.ConfigFactory
import akka.actor.{ ActorRef, ActorSystem, Props }
import akka.http.Http
import akka.http.marshalling.ToResponseMarshallable.apply
import akka.http.model.StatusCodes
import akka.http.server.Directive.{ addByNameNullaryApply, addDirectiveApply }
import akka.http.server.Directives._
import akka.http.server.Route
import akka.http.server.RouteResult.route2HandlerFlow
import akka.pattern.ask
import akka.stream.FlowMaterializer
import akka.stream.scaladsl.Sink
import akka.util.Timeout
import akka.stream.ActorFlowMaterializer

object Status {
  case class Success()
  case class Error(error: String)
}

sealed abstract trait Message
case class TransportMessage(msg: String) extends Message

object ApplicationMain extends App {

  implicit val system = ActorSystem("FerryActorSystem")
  implicit val cc = ConnectionContext()
  import cc.actorFlowMaterializer
  implicit val timeout = Timeout(5.seconds)
  val boatActor = system.actorOf(BoatActor.props, "boatActor")

  val config = ConfigFactory.load()
  private def ip = config.getString("ferry-service.ip")
  private def port = config.getInt("ferry-service.port")

  Http(system).bindAndHandle(routeFerry(boatActor), ip, port)
  StatusService.signalStartedOrExit

  private def routeFerry(
    ferry: ActorRef)(implicit ec: ExecutionContext): Route =
    path("transport") {
      get {
        complete {
          ferry ask TransportMessage("Hi") map {
            case Status.Success =>
              Future.successful(StatusCodes.OK)
            case Status.Error =>
              Future.failed(new Throwable(StatusCodes.BadRequest.value))
          }
        }
      }
    }

}