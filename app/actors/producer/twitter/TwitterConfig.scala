package actors.producer.twitter

import java.io.File
import twitter4j.conf.{ ConfigurationBuilder, Configuration }
import com.typesafe.config.ConfigFactory
import twitter4j.{ TwitterStreamFactory, TwitterStream }

class TwitterConfig(file: File) {

  def getConfig(): Configuration = {
    val configFile = ConfigFactory.parseFile(file)
    val consumerKey = configFile.getString("consumerKey")
    val consumerSecret = configFile.getString("consumerSecret")
    val accessToken = configFile.getString("authAccessToken")
    val accessTokenSecret = configFile.getString("authAccessTokenSecret")
    val config = new ConfigurationBuilder()
      .setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(consumerSecret)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret)
      .build
    config
  }

  def getStream(): TwitterStream = {
    new TwitterStreamFactory(getConfig()).getInstance()
  }
}
