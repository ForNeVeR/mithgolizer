package me.fornever.mithgolizer.configuration

import java.io.{FileInputStream, InputStreamReader, Reader}
import java.nio.file.Path
import java.util.Properties

import scala.language.postfixOps

object Configuration {

  def initialize(path: Path): Unit = {
    configPath = Some(path)
  }

  private def openReader(): Reader = {
    configPath.map(path => new InputStreamReader(new FileInputStream(path.toFile), "UTF8"))
      .getOrElse(sys.error(s"Configuration not found at '$configPath'."))
  }

  private var configPath: Option[Path] = None

  private lazy val properties = {
    val properties = new Properties()
    val reader = openReader()
    try {
      properties.load(reader)
    } finally {
      reader.close()
    }

    properties
  }

  lazy val login = properties.getProperty("login")
  lazy val password = properties.getProperty("password")
  lazy val server = properties.getProperty("server")
  lazy val nickname = properties.getProperty("nickname")

  lazy val room = properties.getProperty("room")

  lazy val filterResource = properties.getProperty("filter.resource").r
  lazy val filterNickname = properties.getProperty("filter.nickname").r
  lazy val filterJid = properties.getProperty("filter.jid").r
}
