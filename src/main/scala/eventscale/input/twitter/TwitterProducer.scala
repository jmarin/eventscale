package eventscale.input.twitter

import akka.stream.actor.ActorProducer
import eventscale.model.twitter.{ StopTwitterStream, StartTwitterStream, TweetEvent }
import akka.actor.{ ActorLogging, Props }
import java.io.File
import twitter4j._
import eventscale.model.twitter.StopTwitterStream
import eventscale.model.twitter.TweetEvent
import eventscale.model.twitter.StartTwitterStream

object TwitterProducer {
  def props(config: TwitterConfig): Props =
    Props(new TwitterProducer(config))
}

class TwitterProducer(config: TwitterConfig) extends ActorProducer[Status] with ActorLogging {
  val twitterStream = config.getStream()

  var counter = 0

  override def receive: Receive = {
    case StartTwitterStream(searchTerms) =>
      log.debug("Start streaming Tweets")
      twitterStream.addListener(statusListener)
      twitterStream.filter(new FilterQuery().track(searchTerms))

    case StopTwitterStream() =>
      log.debug("Stop streaming Tweets")
      twitterStream.cleanUp()
      twitterStream.shutdown()

    case status: Status =>
      if (isActive && totalDemand > 0)
        onNext(status)
      counter += 1
  }

  def statusListener = new StatusListener {
    override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = ???

    override def onStatus(status: Status): Unit = self ! status

    override def onScrubGeo(userId: Long, upToStatusId: Long): Unit = ???

    override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = ???

    override def onException(ex: Exception): Unit = ???

    override def onStallWarning(warning: StallWarning): Unit = ???
  }
}
