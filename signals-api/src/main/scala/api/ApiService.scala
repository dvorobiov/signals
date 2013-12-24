package spray.examples

import scala.concurrent.duration._
import akka.actor._

import spray.routing.{HttpService, RequestContext}
import spray.json._
import DefaultJsonProtocol._

class ApiService extends Actor with BookmarksService {

  def actorRefFactory = context
  val receiveTimeout: Duration = 1 second

  def receive = runRoute(bookmarksRoutes ~ defaultRoutes)

  val defaultRoutes = pathSingleSlash {
      complete("not supported")
    }
}


// this trait defines our service behavior independently from the service actor
trait BookmarksService extends HttpService {
  implicit val executionContext = actorRefFactory.dispatcher

  val bookmarksRoutes = {
    path("bookmarks") {
      get {
        complete("get")
      } ~
      post {
        complete("post")
      }
    }
  }
}
