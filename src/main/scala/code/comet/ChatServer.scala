package code
package comet
import net.liftweb._
import http._
import actor._
/**
 * A singleton that provides chat features to all clients.
 * It's an Actor so it's thread-safe because only one
 * message will be processed at once.
 */
object ChatServer extends LiftActor with ListenerManager {
  private var message = "" // private state

  def createUpdate = message
  /**
   * process messages that are sent to the Actor.  In
   * this case, we're looking for Strings that are sent
   * to the ChatServer.  We append them to our Vector of
   * messages, and then update all the listeners.
   */
  override def lowPriority = {
    case s: String => message = s; updateListeners()
  }
}