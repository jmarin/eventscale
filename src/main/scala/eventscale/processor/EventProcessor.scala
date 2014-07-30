package eventscale.processor

import akka.actor.ActorRef
import org.reactivestreams.api.{ Consumer, Producer }
import akka.stream.FlowMaterializer
import akka.stream.scaladsl.Flow
import akka.stream.actor.{ ActorProducer, ActorConsumer }
import eventscale.model.Event

trait EventProcessor {

  def producer[T](actorRef: ActorRef): Producer[T] = ActorProducer(actorRef)

  def consumer[T](actorRef: ActorRef): Consumer[T] = ActorConsumer(actorRef)

  def process[T](p: Producer[T], c: Consumer[T])(implicit m: FlowMaterializer): Unit =
    Flow(p).produceTo(m, c)

  def process[T](p: Producer[T], f: (T) => Boolean, c: Consumer[T])(implicit m: FlowMaterializer): Unit =
    Flow(p).filter(f).produceTo(m, c)
}
