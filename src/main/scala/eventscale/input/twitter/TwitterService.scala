package eventscale.input.twitter

import core.BaseApp
import akka.actor.{ ActorSystem, Actor }
import java.io.File
import eventscale.model.twitter.{ TweetEvent, StartTwitterStream }
import twitter4j.Status
import org.reactivestreams.api.{ Consumer, Producer }
import akka.stream.actor.{ ActorConsumer, ActorProducer }
import akka.stream.{ FlowMaterializer, MaterializerSettings }
import akka.stream.scaladsl.Flow

object TwitterService extends BaseApp {
  override protected def run(system: ActorSystem, opts: Map[String, String]): Unit = {
    val twitterService = system.actorOf(
      TwitterProducer.props(
        new TwitterConfig(
          new File("src/main/resources/twitter-config.conf"))))

    twitterService ! StartTwitterStream(Array("iphone"))

    val tweetConsumer: Consumer[Status] = ActorConsumer(
      system.actorOf(TwitterConsumer.props())
    )

    val tweetProducer: Producer[Status] = ActorProducer(twitterService)

    implicit val sys: ActorSystem = system

    implicit val executor = system.dispatcher

    val m = FlowMaterializer(MaterializerSettings())

    Flow(tweetProducer).produceTo(m, tweetConsumer)

  }
}
