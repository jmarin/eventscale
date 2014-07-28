package eventscale.model.twitter

import twitter4j.Status
import eventscale.model.InputEvent

case class TweetEvent(status: Status) extends InputEvent
case class StartTwitterStream(searchTerms: Array[String])
case class StopTwitterStream()