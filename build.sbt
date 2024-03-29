import com.typesafe.sbt.web.SbtWeb

name := """eventscale"""

version in ThisBuild := "0.0.1"

scalaVersion in ThisBuild := "2.11.1"

org.scalastyle.sbt.ScalastylePlugin.Settings

lazy val backend = project

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb)

scalaVersion := "2.11.1"

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation", "-feature")

pipelineStages := Seq(rjs, digest)

lazy val backend = project.in(file("backend"))
  .settings(
    name := "backend",
    libraryDependencies ++= Dependencies.backend,
    javaOptions in run ++= Seq("-Xms128m", "-Xmx1024m"),
    fork in run := true
  )

lazy val frontend = project.in(file("frontend"))
  .enablePlugins(PlayScala, SbtWeb)
  .settings(
    name := "frontend",
    libraryDependencies ++= (Dependencies.frontend ++ Seq(filters, cache)),
    pipelineStages := Seq(rjs, digest, gzip),
    RjsKeys.paths += ("jsRoutes" -> ("/jsRoutes" -> "empty:"))
  )

initialCommands := """import actors.boot._""".stripMargin

addCommandAlias("n1", "runMain actors.boot.EventscaleCluster -Dakka.remote.netty.tcp.port=2551")

addCommandAlias("n2", "runMain actors.boot.EventscaleCluster")

addCommandAlias("twitter-sample", "runMain actors.producer.twitter.TwitterSample -Dtwitter4j.loggerFactory=twitter4j.NullLoggerFactory -Dakka.remote.netty.tcp.port=2551 -Dakka.remote.netty.tcp.hostname=127.0.0.1 -Dclustering.seed-ip=127.0.0.1 -Dclustering.seed-port=2551")

addCommandAlias("twitter-sample", "runMain eventscale.producer.twitter.TwitterSample -Dtwitter4j.loggerFactory=twitter4j.NullLoggerFactory -Dakka.remote.netty.tcp.port=2551 -Dakka.remote.netty.tcp.hostname=127.0.0.1 -Dclustering.seed-ip=127.0.0.1 -Dclustering.seed-port=2551")

