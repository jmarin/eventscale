package eventscale.input.twitter

import core.BaseApp
import akka.actor.{ ActorSystem, Actor }
import java.io.File
import eventscale.model.twitter.StartTwitterStream

object TwitterService extends BaseApp {
  override protected def run(system: ActorSystem, opts: Map[String, String]): Unit = {
    val tweetService = system.actorOf(
      TweetsProducer.props(
        new TwitterConfig(
          new File("src/main/resources/twitter-config.conf"))))
    tweetService ! StartTwitterStream(Array("iPhone"))
  }
}
