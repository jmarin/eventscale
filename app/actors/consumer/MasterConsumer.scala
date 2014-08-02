package actors.consumer

import actors.MasterActor
import akka.actor.Props

object MasterConsumer {
  def props():Props = Props(new MasterConsumer)
}


class MasterConsumer extends MasterActor {
  override def receive: Receive = {
    case _ => ???
  }
}

