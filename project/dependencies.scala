import sbt._

object Dependencies {

  object Version {
    val akka = "2.3.4"
    val akkaStreams = "0.4"
    val logback = "1.1.2"
    val specs = "2.3.13"
    val twitter4j = "4.0.2"
    val require = "2.1.11-1"
    val bootstrap = "3.2.0"
    val angular = "1.3.0-beta.17"
    val d3 = "3.4.11"
    val underscore = "1.6.0-3"
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
    "org.webjars" % "requirejs" % Version.require,
    "org.webjars" % "bootstrap" % Version.bootstrap exclude ("org.webjars", "jquery"),
    "org.webjars" % "bootswatch-readable" % Version.bootstrap exclude ("org.webjars", "jquery"),
    "org.webjars" % "angularjs" % Version.angular exclude ("org.webjars", "jquery"),
    "org.webjars" % "d3js" % Version.d3,
    "org.webjars" % "underscorejs" % Version.underscore
  )

  val streams = Seq(
    "org.twitter4j" % "twitter4j-stream" % Version.twitter4j,
    "com.typesafe.akka" %% "akka-persistence-experimental" % Version.akka,
    "com.typesafe.akka" % "akka-stream-experimental_2.11" % Version.akkaStreams,
    "com.typesafe.akka" % "akka-http-core-experimental_2.11" % Version.akkaStreams
  )

}
