package api.actors

import akka.actor.{Actor, ActorLogging}
import api.Create
import domain.BookmarkCreate

class BookmarkActor extends Actor with ActorLogging {
  def receive = {
    case Create(bookmark: BookmarkCreate) => {
      // check db
      // if created => return conflict
      // return new item

      sender ! "hello from actor"
    }
    case _ => {}
  }
}
