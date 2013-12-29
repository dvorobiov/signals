package spray.examples

import scala.concurrent.duration._

import akka.actor.Actor
import spray.http._
import domain.{BookmarkCreate, Bookmark}

import net.liftweb.json.{Serialization, NoTypeHints, Formats, DefaultFormats}
import net.liftweb.json.ext.JodaTimeSerializers
import spray.routing._
import spray.httpx.LiftJsonSupport
import org.joda.time.DateTime

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

  implicit val liftJsonFormats: Formats = Serialization.formats(NoTypeHints) ++ JodaTimeSerializers.all

  val bookmarksRoutes = respondWithMediaType(MediaTypes.`application/json`) {
    path("bookmarks") {
      post {
        entity(as[BookmarkCreate]) { bookmark =>
          complete {
            s"done with name: ${bookmark.description}"
            // call actor to create a new bookmark, return created or conflict
          }
        }
      } ~
      get {
        complete {
          // call actor to return from database, check the way
          Bookmark(1, "test", Some("hello"), List("hello"), DateTime.now)
        }
      } ~
      delete {
        complete {
          null
        }
      } ~
      put {
        complete {
          null
        }
      }

      }
  }
}
