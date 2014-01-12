package api.endpoints

import api.SignalsHttpService
import spray.http.{MediaTypes}
import domain.{Bookmark, BookmarkCreate}
import scala.concurrent.ExecutionContext
import akka.actor.ActorRef
import api.common._
import akka.pattern._

// TODO: to provide actor not connection after finishing testing
class BookmarksEndpoint(actor: ActorRef)(implicit executionContext: ExecutionContext) extends SignalsHttpService {

  val routes = respondWithMediaType(MediaTypes.`application/json`) {

    path("bookmarks") {
      post {
        handleWith { bookmarkCreate: BookmarkCreate =>
          (actor ? Create(bookmarkCreate)).mapTo[Either[SignalsHttpResponse[String], SignalsHttpResponse[Bookmark]]].map {
            case Left(SignalsHttpResponse(text, status)) => status -> text
            case Right(SignalsHttpResponse(bookmark, status)) => status -> bookmark
          }
        }
      } ~
      get {
        complete {
          (actor ? GetAll).mapTo[Either[SignalsHttpResponse[String], SignalsHttpResponse[Bookmark]]].map {
            case Left(SignalsHttpResponse(text, status)) => status -> text
            case Right(SignalsHttpResponse(bookmark, status)) => status -> bookmark
          }
        }
      } ~
      delete {
        complete {
          actor ? Delete("false")
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

