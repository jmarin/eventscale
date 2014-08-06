package eventscale.producer.twitter

import core.BaseApp
import akka.actor.{ ActorSystem, Actor, ActorRef }
import java.io.File

import eventscale.model.twitter.{ TweetEvent, StartTwitterStream }
import twitter4j.Status
import org.reactivestreams.api.{ Consumer, Producer }
import akka.stream.actor.{ ActorConsumer, ActorProducer }
import akka.stream.{ FlowMaterializer, MaterializerSettings }
import akka.stream.scaladsl.Flow
import eventscale.model.Event
import eventscale.model.twitter.TweetEvent
import eventscale.service.EventProcessor
import eventscale.consumer.ConsoleConsumer

object TwitterSample extends BaseApp with EventProcessor {
  override protected def run(system: ActorSystem, opts: Map[String, String]): Unit = {
    val twitterStream = system.actorOf(
      TwitterProducer.props(
        new TwitterConfig(
          new File("src/main/resources/twitter-config.conf"))))

    //twitterStream ! StartTwitterStream(Some(Array("iphone")))
    twitterStream ! StartTwitterStream(None)

    val tweetProducer: Producer[TweetEvent] = producer(twitterStream)

    val c = system.actorOf(ConsoleConsumer.props(1000))

    val tweetConsumer: Consumer[TweetEvent] = consumer(c)

    implicit val s: ActorSystem = system

    implicit val m = FlowMaterializer(MaterializerSettings())

    def isGeolocation(e: TweetEvent): Boolean = e.status.getGeoLocation != null

    //Process, shows tweets on console output
    process(tweetProducer, tweetConsumer)

    //Process with a filter, showing only tweets that have geolocation
    //process(tweetProducer, isGeolocation, tweetConsumer)

  }

}
