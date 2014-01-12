package spray.examples

import akka.actor.{Props, Actor}
import spray.routing._
import api.endpoints.BookmarksEndpoint
import com.typesafe.config.ConfigFactory
import reactivemongo.api._
import api.actors.BookmarkActor


class MainEndpoint extends Actor with HttpService with Directives {
  val actorRefFactory = context
  implicit val executionContext = context.dispatcher
  val system = context.system

  var conf = ConfigFactory.load
  val mongoUrl = conf.getString("mongodb.conn_uri")
  val dbName = conf.getString("mongodb.db_name")

  val driver = new MongoDriver
  val connection = driver.connection(List(mongoUrl))
  lazy val db = connection.db(dbName)

  def receive = runRoute(routes)

  val bookmarkRef = context.watch(system.actorOf(Props[BookmarkActor](new BookmarkActor(db)), "bookmark"))


  lazy val routes =
    defaultRoutes ~
    new BookmarksEndpoint(bookmarkRef)(executionContext).routes

  val defaultRoutes = pathSingleSlash {
    complete("not supported")
  }
}
