package eventscale.model

import twitter4j.Status

trait Event
trait InputEvent extends Event
trait ProcessedEvent extends Event
trait OutputEvent extends Event