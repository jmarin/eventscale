package eventscale.consumer

import akka.actor.{ Props, ActorLogging }
import akka.stream.actor.ActorConsumer
import akka.stream.actor.ActorConsumer.{ OnNext, MaxInFlightRequestStrategy, RequestStrategy }
import eventscale.model.Event

object ConsoleConsumer {
  def props(maxInFlight: Int): Props =
    Props(new ConsoleConsumer(maxInFlight))

}

class ConsoleConsumer(maxInFlight: Int) extends ActorConsumer with ActorLogging {

  var inFlight = 0

  override protected def requestStrategy: RequestStrategy = new MaxInFlightRequestStrategy(maxInFlight) {
    override def inFlightInternally: Int = inFlight
  }

  override def receive: Receive = {
    case OnNext(event: Event) =>
      inFlight += 1
      processEvent(event)

    case _ => log.debug("Unknown message received")
  }

  private def processEvent(event: Event) = {
    println(event)
    inFlight -= 1
  }

}
