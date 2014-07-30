package eventscale.processor

import akka.actor.ActorRef
import org.reactivestreams.api.{ Consumer, Producer }
import akka.stream.actor.{ ActorProducer, ActorConsumer }
import eventscale.model.Event

trait EventProcessor {

  def getProducer[P <: Event](actorRef: ActorRef): Producer[Event] = ActorProducer(actorRef)

  def getConsumer[C <: Event](actorRef: ActorRef): Consumer[Event] = ActorConsumer(actorRef)

}
