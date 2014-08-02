package model.twitter

import twitter4j.Status
import model.Event

case class TweetEvent(status: Status) extends Event {
  override def toString = status.getText
}
case class StartTwitterStream(searchTerms: Option[Array[String]])
case class StopTwitterStream()
