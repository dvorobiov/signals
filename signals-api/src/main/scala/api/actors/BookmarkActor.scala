package api.actors

import akka.actor.{Actor, ActorLogging}
import domain.{Bookmark, BookmarkCreate}
import reactivemongo.api.DefaultDB
import scala.concurrent.ExecutionContext
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import scala.util.{Success, Failure}
import reactivemongo.api.collections.default.BSONCollection
import spray.http.StatusCodes._
import api.common._
import org.joda.time.DateTime

class BookmarkActor(db: => DefaultDB)(implicit executionContext: ExecutionContext) extends Actor with ActorLogging {
  lazy val collection = db.collection[BSONCollection]("bookmarks")
  def idQuery(id: String) = BSONDocument("_id" -> id)

  def receive = {
    case Create(bookmark: BookmarkCreate) =>
      val bookmarkToCreate = Bookmark.fromBookmarkCreate(bookmark)
      val _sender = this.sender
      collection.insert(bookmarkToCreate).andThen {
        case Failure(e) => _sender ! Left(SignalsHttpResponse("cannot save new bookmark now", InternalServerError))
        case Success(_) => _sender ! Right(SignalsHttpResponse(bookmarkToCreate, Created))
      }

    case Update(bookmark: Bookmark) =>
      val _sender = this.sender
      collection.update(idQuery(bookmark.id.toString), bookmark).andThen {
        case Failure(e) => _sender ! Left(SignalsHttpResponse("cannot update", InternalServerError))
        case Success(_) => _sender ! Right(SignalsHttpResponse("", OK))
      }

    case Delete(id: String) =>
      val _sender = this.sender
      collection.remove(idQuery(id)).andThen {
        case Failure(e) => _sender ! Left(SignalsHttpResponse("cannot delete", InternalServerError))
        case Success(_) => _sender ! Right(SignalsHttpResponse("", OK))
      }

    case GetAll =>
      val _sender = this.sender
      collection
        .find()
        .cursor[BSONDocument]
        .collect[List]()
        .andThen {
          case Failure(e) => _sender ! Left(SignalsHttpResponse("cannot retrieve", InternalServerError))
          case Success(bookmarks: List[Bookmark]) => _sender ! Right(SignalsHttpResponse(bookmarks, OK))
        }

    case Get(id: String) =>
      val _sender = this.sender
      val bookmarkFutureOpt = collection
        .find(idQuery(id))
        .cursor[BSONDocument]
        .headOption

      val response = bookmarkFutureOpt map { bookmarkOpt =>
        bookmarkOpt.map { bookmark =>
          Right(SignalsHttpResponse(bookmark, OK))
        } getOrElse(Left(SignalsHttpResponse("", NotFound)))
      }
      _sender ! response

    case _ => throw new NotImplementedException
  }

  private def alreadyExists(bookmarkId: BSONObjectID) = false
}
