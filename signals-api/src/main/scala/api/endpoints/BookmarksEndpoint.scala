package api.endpoints

import api.SignalsHttpService
import spray.http.MediaTypes
import domain.{Bookmark, BookmarkCreate}
import org.joda.time.DateTime
import scala.concurrent.ExecutionContext
import reactivemongo.api.{DefaultDB}
import reactivemongo.bson.BSONObjectID
import reactivemongo.api.collections.default.BSONCollection
import scala.util.{Success, Failure}

// TODO: to provide actor not connection after finishing testing
class BookmarksEndpoint(db: => DefaultDB)(implicit executionContext: ExecutionContext) extends SignalsHttpService {

  val routes = respondWithMediaType(MediaTypes.`application/json`) {
  def collection = db.collection[BSONCollection]("bookmarks")
    path("bookmarks") {
      post {
        entity(as[BookmarkCreate]) { bookmarkCreate =>
          complete {
            val bookmark = Bookmark.fromBookmarkCreate(bookmarkCreate)
            collection.insert(bookmark).andThen {
              case Failure(e) => complete("error")
              case Success(lastError) => complete("super!")
            }
            // call actor to create a new bookmark, return created or conflict
          }
        }
      } ~
      get {
        complete {
          // call actor to return from database, check the way
          Bookmark(BSONObjectID.generate, "test", Some("hello"), List("hello"), DateTime.now)
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

