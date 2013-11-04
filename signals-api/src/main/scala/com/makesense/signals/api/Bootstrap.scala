import akka.actor.{ActorRef, ActorSystem}
import akka.io.IO
import spray.can.Http

implicit val system = ActorSystem()

val listener: ActorRef = ???

IO(Http) ! Http.Bind(listener, interface = "localhost", port = 8080)