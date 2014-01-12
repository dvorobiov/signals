package api.actors

import akka.actor.{Actor, ActorLogging}
import domain.{Bookmark, BookmarkCreate}
import reactivemongo.api.DefaultDB
import scala.concurrent.ExecutionContext
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import reactivemongo.bson.BSONObjectID
import scala.util.{Success, Failure}
import reactivemongo.api.collections.default.BSONCollection
import spray.http.StatusCodes._
import api.common._
import org.joda.time.DateTime

class BookmarkActor(db: => DefaultDB)(implicit executionContext: ExecutionContext) extends Actor with ActorLogging {
  lazy val collection = db.collection[BSONCollection]("bookmarks")
  def receive = {
    case Create(bookmark: BookmarkCreate) =>
      val bookmarkToCreate = Bookmark.fromBookmarkCreate(bookmark)
      val _sender = this.sender
      collection.insert(bookmarkToCreate).andThen {
        case Failure(e) => _sender ! Left(SignalsHttpResponse("cannot save new bookmark now", InternalServerError))
        case Success(lastError) => _sender ! Right(SignalsHttpResponse(bookmarkToCreate, Created))
      }

    case Update(bookmark: Bookmark) =>

    case Delete(id: String) =>

    case GetAll =>
      val _sender = this.sender
      _sender ! Right(SignalsHttpResponse(Bookmark(BSONObjectID.generate, "test", Some("hello"), List("hello"), DateTime.now), OK))

    case Get(id: String) =>

    case _ => throw new NotImplementedException
  }

  private def alreadyExists(bookmarkId: BSONObjectID) = false
}
