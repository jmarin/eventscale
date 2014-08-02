package actors.boot

import play.api.Logger
import akka.actor.{ActorSystem, Actor, Props}
import akka.cluster.ClusterEvent._
import akka.cluster.ClusterEvent.MemberUp
import akka.cluster.ClusterEvent.UnreachableMember
import akka.cluster.ClusterEvent.CurrentClusterState
import akka.cluster.ClusterEvent.MemberRemoved
import com.typesafe.config.ConfigFactory
import akka.cluster.Cluster
import actors.{ Start, Stop }
import actors.producer.MasterProducer
import actors.consumer.MasterConsumer

object EventscaleCluster extends App {

  val system = ActorSystem("eventscale-system")
  def cluster = Cluster(system)
  val listener = system.actorOf(Props[ClusterListener], "cluster-listener")

  start()

  def start() = {
    cluster.subscribe(listener, classOf[ClusterDomainEvent])
    Logger.info("Cluster initialized")
    listener ! Start()
  }

  def stop() = {
    listener ! Stop()
    system.shutdown()
    Logger.info("Cluster stopped")
  }

}

class ClusterListener extends Actor {
  def receive: Receive = {
    case state: CurrentClusterState =>
    case MemberUp(member) =>
      Logger.info(s"Member is Up: ${member}")
    case UnreachableMember(member) =>
      Logger.warn(s"Member detected as unreachable; ${member}")
    case MemberRemoved(member, previousStatus) =>
      Logger.warn(s"Member is removed: ${member.address} after ${previousStatus}")
    case _: ClusterDomainEvent => //ignore
    case Start() =>
      context.actorOf(Props[MasterProducer], "master-producer")
      context.actorOf(Props[MasterConsumer], "master-consumer")
    case Stop() =>
      context.stop(self)

  }

}