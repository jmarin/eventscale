package actors.producer

import actors.MasterActor
import akka.actor.Props

object MasterProducer {
  def props():Props = Props(new MasterProducer)
}

class MasterProducer extends MasterActor {

  override def receive: Receive = {
    case _ => ???
  }
}