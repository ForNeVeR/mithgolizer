package me.fornever.mithgolizer

import java.nio.file.Paths

import me.fornever.mithgolizer.configuration.Configuration
import me.fornever.mithgolizer.protocol.XmppProtocol

object Application extends App {

  initializeConfiguration(args)

  XmppProtocol.connect()
  println("Press any key to stop")
  System.in.read()

  private def initializeConfiguration(args: Array[String]) {
    val configPath = args match {
      case Array(config, _*) => config
      case _ => "mithgolizer.properties"
    }

    Configuration.initialize(Paths.get(configPath))
  }
}
