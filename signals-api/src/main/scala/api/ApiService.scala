package spray.examples

import scala.concurrent.duration._

import akka.actor.Actor
import spray.http._
import spray.http.MediaTypes._
import domain.{BookmarkCreate, Bookmark}
import net.liftweb.json.JsonParser._
import net.liftweb.json.Serialization._

import net.liftweb.json.{Formats, DefaultFormats}
import spray.httpx.unmarshalling._
import spray.routing._
import spray.httpx.LiftJsonSupport

class ApiService extends Actor with BookmarksService {

  def actorRefFactory = context
  val receiveTimeout: Duration = 1 second

  def receive = runRoute(bookmarksRoutes ~ defaultRoutes)

  val defaultRoutes = pathSingleSlash {
    complete("not supported")
  }
}


// this trait defines our service behavior independently from the service actor
trait BookmarksService extends HttpService with LiftJsonSupport {
  implicit val executionContext = actorRefFactory.dispatcher

  implicit val liftJsonFormats: Formats = DefaultFormats

  val bookmarksRoutes = respondWithMediaType(MediaTypes.`application/json`) {
    path("bookmarks") {
      post {
        entity(as[BookmarkCreate]) { bookmark =>
          complete {
            s"done with name: ${bookmark.description}"
          }
        }
      } ~
      get {
        complete {
          Bookmark(1, "test", Some("hello"), List())
        }
      }

      }
  }
}
