package code
package comet
import net.liftweb._
import http._
import actor._
import code.model.User
/**
 * A singleton that provides chat features to all clients.
 * It's an Actor so it's thread-safe because only one
 * message will be processed at once.
 */
object ConfirmFriendsConvServer extends LiftActor with ListenerManager {
  private var userID = 0L
  def createUpdate = userID
  override def lowPriority = {
    case  l : Long => 
        userID = l; 
	    println("testing users "); 
	    println(userID);
	    println("===============");
	    updateListeners()
  }
}