package com.makesense.signals.api

import scala.concurrent.duration._
import akka.pattern.ask
import akka.util.Timeout
import akka.actor._
import spray.can.Http
import spray.can.server.Stats
import spray.util._
import spray.http._
import HttpMethods._
import MediaTypes._


class ApiService extends Actor with SprayActorLogging {
  implicit val timeout: Timeout = 1.second
  import context.dispatcher // ExecutionContext for the futures and scheduler

  def receive = {
    case _: Http.Connected => sender ! Http.Register(self)

    case HttpRequest(GET, Uri.Path("/chunk"), _, _, _) =>
      sender ! HttpResponse(entity = "PONG!")
  }
}
