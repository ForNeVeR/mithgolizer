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
        val nickname = StringUtils.parseResource(participant)
        val occupant = muc.getOccupant(participant)
        val occupantJid = occupant.getJid

        val resource = StringUtils.parseResource(occupantJid)
        if (Configuration.filterResource.pattern.matcher(resource).matches()) {
          println(StringUtils.parseBareAddress(occupantJid) + s" ($room): filtered by resource")
          muc.banUser(occupantJid, null)
        } else if (Configuration.filterJid.pattern.matcher(occupantJid).matches) {
          println(StringUtils.parseBareAddress(occupantJid) + s" ($room): filtered by JID")
          muc.banUser(occupantJid, null)
        } else if (Configuration.filterNickname.pattern.matcher(nickname).matches()) {
          println(StringUtils.parseBareAddress(occupantJid) + s" ($room/$nickname): filtered by nickname")
          muc.kickParticipant(nickname, null)
        }
      }
    })

    muc.join(Configuration.nickname)
    println(s"Logged into $room")
  }
}
