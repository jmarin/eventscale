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
import eventscale.processor.EventProcessor
import eventscale.consumer.ConsoleConsumer

object TwitterService extends BaseApp with EventProcessor {
  override protected def run(system: ActorSystem, opts: Map[String, String]): Unit = {
    val twitterService = system.actorOf(
      TwitterProducer.props(
        new TwitterConfig(
          new File("src/main/resources/twitter-config.conf"))))

    //twitterService ! StartTwitterStream(Some(Array("iphone")))
    twitterService ! StartTwitterStream(None)

    val tweetProducer = getProducer(twitterService)

    val consumer = system.actorOf(ConsoleConsumer.props(1000))

    val tweetConsumer = getConsumer(consumer)

    implicit val sys: ActorSystem = system

    implicit val executor = system.dispatcher

    val m = FlowMaterializer(MaterializerSettings())

    Flow(tweetProducer)
      .filter(e => e match {
        case e: TweetEvent => e.status.getText.length < 70
        case _ => false
      }
      )
      .filter(e => e match {
        case e: TweetEvent => e.status.getGeoLocation != null
        case _ => false
      })
      .produceTo(m, tweetConsumer)

  }

}
