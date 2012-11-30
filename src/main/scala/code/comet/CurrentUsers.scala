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

class CurrentUsers extends CometActor with CometListener {
  private var userNames: List[String] = List()
  
  def registerWith = ChatServer
  
  override def lowPriority = {
      case s:String => {
		  val conversations = Conversation.findAll()
	      val conversationID:Long = User.currentUser.open_!.currentConversation.get
	      val users = User.findAll(By( User.currentConversation, conversationID))
	      users.foreach( usr => println( usr.firstName.get + " " + usr.lastName.get))
	      userNames = users.map( ( user : User ) => user.firstName.get + " " + user.lastName.get)
	      print("redoing conversations now!")
      }
      reRender()
  }

  def render = "li *" #> userNames & ClearClearable
}