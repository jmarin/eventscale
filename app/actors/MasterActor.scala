package actors

import akka.actor.{ActorLogging, Actor}

class MasterActor extends Actor with ActorLogging {

  override def preStart() {
    log.debug(s"Starting actor at ${self.path}")
  }

  override def receive: Receive = ???
}