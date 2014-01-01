package spray.examples

import scala.concurrent.duration._
import akka.actor.Actor
import spray.routing._
import api.endpoints.BookmarksEndpoint

class ApiService extends Actor with HttpService with Directives {
  val actorRefFactory = context
  val executionContext = context.dispatcher

  val receiveTimeout: Duration = 1 second

  def receive = runRoute(routes)

  lazy val routes =
    defaultRoutes ~
    new BookmarksEndpoint(null)(executionContext).routes

  val defaultRoutes = pathSingleSlash {
    complete("not supported")
  }
}
