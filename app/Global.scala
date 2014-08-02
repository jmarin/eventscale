import play.api._
import actors.boot.EventscaleCluster

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    EventscaleCluster.main(Array(""))
  }

  override def onStop(app: Application) {
    EventscaleCluster.stop()
  }
  
}
