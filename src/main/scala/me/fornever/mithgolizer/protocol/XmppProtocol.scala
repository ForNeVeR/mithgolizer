package me.fornever.mithgolizer.protocol

import me.fornever.mithgolizer.configuration._
import org.jivesoftware.smack.util.StringUtils
import org.jivesoftware.smack.{ConnectionConfiguration, XMPPConnection}
import org.jivesoftware.smackx.muc.{DefaultParticipantStatusListener, MultiUserChat}

import scala.language.postfixOps

object XmppProtocol {

  var connection: XMPPConnection = null

  def connect(): Unit = {
    val server = Configuration.server
    println(s"Connecting to $server")

    val configuration = new ConnectionConfiguration(server)
    configuration.setReconnectionAllowed(true)

    connection = new XMPPConnection(configuration)
    val chatManager = connection.getChatManager

    connection.connect()

    connection.login(Configuration.login, Configuration.password)

    val rooms = Configuration.room.split(",")

    rooms.foreach(join)
  }

  def join(room: String): Unit = {
    val muc = new MultiUserChat(connection, room)
    muc.addParticipantStatusListener(new DefaultParticipantStatusListener {

      override def joined(participant: String): Unit = {
        val occupant = muc.getOccupant(participant)

        val resource = StringUtils.parseResource(occupant.getJid)
        if (resource == Configuration.resource) {
          println(StringUtils.parseBareAddress(occupant.getJid) + s" ($room)")
          muc.banUser(occupant.getJid, null)
        }
      }
    })

    muc.join(Configuration.nickname)
    println(s"Logged into $room")
  }
}
