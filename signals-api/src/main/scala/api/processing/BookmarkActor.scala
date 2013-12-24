package api.processing

import akka.actor.{Actor, ActorLogging}
import api.Create

class BookmarkActor extends Actor with ActorLogging {
  def receive = {
    case Create(json) => {
      log.info(s"Create ${json}")
      sender ! "hello from actor"
    }
    case _ => {}
  }
}
