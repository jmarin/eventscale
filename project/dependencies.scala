import sbt._

object Dependencies {

  object Version {
    val akka = "2.3.4"
    val akkaStreams = "0.4"
    val logback = "1.1.2"
    val specs = "2.3.13"
    val twitter4j = "4.0.2"
    val requirejs = "2.1.11-1"
  }

  lazy val frontend = common ++ webjars
  lazy val backend = common ++ streams

  val common = Seq(
    "com.typesafe.akka" %% "akka-actor" % Version.akka,
    "com.typesafe.akka" %% "akka-remote" % Version.akka,
    "com.typesafe.akka" %% "akka-cluster" % Version.akka,
    "com.typesafe.akka" %% "akka-slf4j" % Version.akka,
    "com.typesafe.akka" %% "akka-testkit" % Version.akka % "test",
    "ch.qos.logback" % "logback-classic" % Version.logback,
    "org.specs2" %% "specs2" % Version.specs % "test"
  )

  val webjars = Seq(
    "org.webjars" % "requirejs" % Version.requirejs  
  )

  val streams = Seq(
    "org.twitter4j" % "twitter4j-stream" % Version.twitter4j,
    "com.typesafe.akka" %% "akka-persistence-experimental" % Version.akka,
    "com.typesafe.akka" % "akka-stream-experimental_2.11" % Version.akkaStreams,
    "com.typesafe.akka" % "akka-http-core-experimental_2.11" % Version.akkaStreams
  )

}
