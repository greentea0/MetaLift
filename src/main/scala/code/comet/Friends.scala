package code
package comet
import net.liftweb._
import http._
import util._
import Helpers._
import java.sql.Date
import code.model._
import code.model.Conversation
import java.util.Calendar
import net.liftweb.mapper._
import net.liftweb.mapper.MappedLongIndex
import net.liftweb.http.js.JE
/**
 * The screen real estate on the browser will be represented
 * by this component.  When the component changes on the server
 * the changes are automatically reflected in the browser.
 */
class Friends extends CometActor with CometListener {
  private var friends: List[User] = User.currentUser.get.friends.toList // private state

  
  def registerWith = FriendServer

  
  override def lowPriority = {
      case s: String => {
          // grab all the conversations for the current user for their current conversation
		  friends = User.currentUser.get.friends.toList
	      // put those conversations into the convo list window
	      reRender()
      }
      
  }

  def render = " li * " #> friends.map{ user => (user.firstName.get + " "  + user.lastName.get) }
}