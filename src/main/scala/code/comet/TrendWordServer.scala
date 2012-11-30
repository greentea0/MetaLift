package code
package comet
import net.liftweb._
import http._
import util._
import Helpers._
import java.sql.Date
import code.model._
import code.model.User
import net.liftweb.mapper._
import net.liftweb.mapper.MappedLongIndex
import net.liftweb.http.js.JE
import net.liftweb.common.Box
import net.liftweb.actor.LiftActor
/**
 * The screen real estate on the browser will be represented
 * by this component.  When the component changes on the server
 * the changes are automatically reflected in the browser.
 */
object TrendWordServer extends LiftActor with ListenerManager {
  
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