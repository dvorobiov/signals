package spray.examples

import scala.concurrent.duration._
import akka.actor.Actor
import spray.routing._
import api.endpoints.BookmarksEndpoint
import reactivemongo.api.MongoDriver
import com.typesafe.config.ConfigFactory
import reactivemongo.bson.BSONDocument
import reactivemongo.api._
import reactivemongo.bson._
import reactivemongo.api.collections.default.BSONCollection


class MainEndpoint extends Actor with HttpService with Directives {
  val actorRefFactory = context
  implicit val executionContext = context.dispatcher

  val receiveTimeout: Duration = 2 seconds
  val driver = new MongoDriver

  var conf = ConfigFactory.load
  val mongoUrl = conf.getString("mongodb.conn_uri")
  val dbName = conf.getString("mongodb.db_name")

  val connection = driver.connection(List(mongoUrl))
  lazy val db = connection.db(dbName)

  def receive = runRoute(routes)

  lazy val routes =
    defaultRoutes ~
    new BookmarksEndpoint(db)(executionContext).routes

  val defaultRoutes = pathSingleSlash {
    complete("not supported")
  }
}
