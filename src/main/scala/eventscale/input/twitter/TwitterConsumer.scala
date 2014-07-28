package eventscale.input.twitter

import akka.stream.actor.ActorConsumer
import akka.stream.actor.ActorConsumer.{ OnNext, MaxInFlightRequestStrategy, RequestStrategy }
import akka.actor.Actor.Receive
import twitter4j.Status
import akka.actor.Props

object TwitterConsumer {
  def props(): Props =
    Props(new TwitterConsumer())
}

class TwitterConsumer extends ActorConsumer {

  private var inFlight = 0

  override protected def requestStrategy: RequestStrategy = new MaxInFlightRequestStrategy(10) {
    override def inFlightInternally: Int = inFlight
  }

  override def receive: Receive = {
    case OnNext(status: Status) =>
      inFlight += 1
      processTweet(status)
    case _ => println("I don't know what this is")
  }

  private def processTweet(status: Status) = {
    println(status.getText)
    inFlight -= 1
  }

  private def processGeoTweet(status: Status) = {
    if (status.getGeoLocation != null) {
      println(status.getGeoLocation.getLatitude + ", "
        + status.getGeoLocation.getLongitude)
    }
    inFlight -= 1
  }
}
