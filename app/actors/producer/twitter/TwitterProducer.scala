package actors.producer.twitter

import akka.stream.actor.ActorProducer
import akka.actor.{ ActorLogging, Props }
import java.io.File
import twitter4j._
import model.twitter.StopTwitterStream
import model.twitter.TweetEvent
import model.twitter.StartTwitterStream

object TwitterProducer {
  def props(config: TwitterConfig): Props =
    Props(new TwitterProducer(config))
}

class TwitterProducer(config: TwitterConfig) extends ActorProducer[TweetEvent] with ActorLogging {
  val twitterStream = config.getStream()

  var counter = 0

  override def postStop(): Unit = {
    closeTwitterStream
  }

  override def receive: Receive = {
    case StartTwitterStream(searchTerms) =>
      log.debug("Start streaming Tweets")
      twitterStream.addListener(statusListener)
      searchTerms match {
        case Some(st) => twitterStream.filter(new FilterQuery().track(st))
        case None => twitterStream.sample()
      }

    case StopTwitterStream() =>
      log.debug("Stop streaming Tweets")
      closeTwitterStream

    case event: TweetEvent =>
      if (isActive && totalDemand > 0)
        onNext(event)
      counter += 1
  }

  def statusListener = new StatusListener {
    override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = ???

    override def onStatus(status: Status): Unit = self ! TweetEvent(status)

    override def onScrubGeo(userId: Long, upToStatusId: Long): Unit = ???

    override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit =
      log.debug("deletion notice")

    override def onException(ex: Exception): Unit = ???

    override def onStallWarning(warning: StallWarning): Unit = ???
  }

  private def closeTwitterStream(): Unit = {
    twitterStream.cleanUp()
    twitterStream.shutdown()
  }
}
