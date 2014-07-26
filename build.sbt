name := "eventscale"

version := "0.1.0"

scalaVersion := "2.11.1"

org.scalastyle.sbt.ScalastylePlugin.Settings

scalariformSettings

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"  
)

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies ++= {
  val akkaVersion = "2.3.4"
  val akkaStreamsVersion = "0.4"
  val logbackVersion = "1.1.2"
  val specsVersion = "2.3.13"
  val twitter4jVersion = "4.0.2"
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
    "org.specs2" %% "specs2" % specsVersion % "test"
  )
}

initialCommands := """import eventscale._""".stripMargin

addCommandAlias("twitter", "runMain eventscale.input.twitter.TwitterService -Dakka.remote.netty.tcp.port=2551 -Dakka.remote.netty.tcp.hostname=127.0.0.1 -Dclustering.seed-ip=127.0.0.1 -Dclustering.seed-port=2551")
