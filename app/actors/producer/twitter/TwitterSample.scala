package actors.producer.twitter

import akka.actor.ActorSystem
import java.io.File
import org.reactivestreams.api.{ Consumer, Producer }
import akka.stream.{ FlowMaterializer, MaterializerSettings }
import model.twitter.{ StartTwitterStream, TweetEvent }
import actors.boot.BaseApp
import actors.processor.EventProcessor
import actors.consumer.ConsoleConsumer

object TwitterSample extends BaseApp with EventProcessor {
  override protected def run(system: ActorSystem, opts: Map[String, String]): Unit = {
    val twitterService = system.actorOf(
      TwitterProducer.props(
        new TwitterConfig(
          new File("conf/twitter-config.conf"))))

    //twitterService ! StartTwitterStream(Some(Array("iphone")))
    twitterService ! StartTwitterStream(None)

    val tweetProducer: Producer[TweetEvent] = producer(twitterService)

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
