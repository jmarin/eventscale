import com.typesafe.sbt.web.SbtWeb

name := """eventscale"""

version := "1.0-SNAPSHOT"

org.scalastyle.sbt.ScalastylePlugin.Settings

lazy val backend = project

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb)

scalaVersion := "2.11.1"

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation", "-feature")

pipelineStages := Seq(rjs, digest)

libraryDependencies ++= {
  val akkaVersion = "2.3.4"
  val akkaStreamsVersion = "0.4"
  val logbackVersion = "1.1.2"
  val specsVersion = "2.3.13"
  val twitter4jVersion = "4.0.2"
  val requirejsVersion = "2.1.14-1"
  val bootstrapVersion = "3.2.0"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-remote" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "com.typesafe.akka" % "akka-stream-experimental_2.11" % akkaStreamsVersion,
    "com.typesafe.akka" % "akka-http-core-experimental_2.11" % akkaStreamsVersion,
    "com.typesafe.akka" %% "akka-persistence-experimental" % akkaVersion,
    "org.twitter4j" % "twitter4j-stream" % twitter4jVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "org.specs2" %% "specs2" % specsVersion % "test",
    "org.webjars" % "requirejs" % requirejsVersion,
    "org.webjars" % "bootstrap" % bootstrapVersion
  )
}

initialCommands := """import actors.boot._""".stripMargin

addCommandAlias("n1", "runMain actors.boot.EventscaleCluster -Dakka.remote.netty.tcp.port=2551")

addCommandAlias("n2", "runMain actors.boot.EventscaleCluster")

addCommandAlias("twitter-sample", "runMain actors.producer.twitter.TwitterSample -Dtwitter4j.loggerFactory=twitter4j.NullLoggerFactory -Dakka.remote.netty.tcp.port=2551 -Dakka.remote.netty.tcp.hostname=127.0.0.1 -Dclustering.seed-ip=127.0.0.1 -Dclustering.seed-port=2551")


