resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.2")

// web plugins

addSbtPlugin("com.typesafe.sbt" % "sbt-coffeescript" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-jshint" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-rjs" % "1.0.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-mocha" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.0")

//Scalastyle plugin
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.4.0")

//Scalariform plugin
addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.3.0")

// Enable this plugin to automatically refresh Chrome when you make changes to your app
addSbtPlugin("com.jamesward" %% "play-auto-refresh" % "0.0.10")
