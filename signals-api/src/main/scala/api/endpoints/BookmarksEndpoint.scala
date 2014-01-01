package api.endpoints

import api.SignalsHttpService
import spray.http.MediaTypes
import domain.{Bookmark, BookmarkCreate}
import org.joda.time.DateTime
import akka.actor.ActorRef
import scala.concurrent.ExecutionContext

class BookmarksEndpoint(bookmarkActor: ActorRef)(implicit executionContext: ExecutionContext) extends SignalsHttpService {

  val routes = respondWithMediaType(MediaTypes.`application/json`) {

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

