package spray.examples

import scala.concurrent.duration._
import akka.actor.Actor
import spray.routing._
import api.endpoints.BookmarksEndpoint
import reactivemongo.api.MongoDriver
import com.typesafe.config.ConfigFactory

class MainEndpoint extends Actor with HttpService with Directives {
  val actorRefFactory = context
  val executionContext = context.dispatcher

  val receiveTimeout: Duration = 2 seconds
  val driver = new MongoDriver

  var conf = ConfigFactory.load
  val mongoUrl = conf.getString("mongodb.conn_uri")
  val dbName = conf.getString("mongodb.db_name")

  val connection = driver.connection(List(mongoUrl))
  def db = connection.db(dbName)

  def receive = runRoute(routes)

  lazy val routes =
    defaultRoutes ~
    new BookmarksEndpoint(db)(executionContext).routes

  val defaultRoutes = pathSingleSlash {
    complete("not supported")
  }
}
