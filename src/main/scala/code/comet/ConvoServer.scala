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
object ConvoServer extends LiftActor with ListenerManager {
  private var upd = -1L // private state

  
  def createUpdate = upd


  override def lowPriority = {
    case l: Long => upd = l; updateListeners()
  }
}