package spray.examples

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http

object Boot extends App {
  implicit val system = ActorSystem("services")

  val service = system.actorOf(Props[MainEndpoint], "api")

  IO(Http) ! Http.Bind(service, "localhost", port = 8080)
}