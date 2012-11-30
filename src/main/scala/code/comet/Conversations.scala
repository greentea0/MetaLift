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
class Conversations extends CometActor with CometListener {
  private var conversations: List[Conversation] = User.currentUser.get.conversations.toList // private state

  
  def registerWith = ConvoServer

  
  override def lowPriority = {
      case -1L => {
          // grab all the conversations for the current user for their current conversation
		  conversations = User.currentUser.get.conversations.toList
	      // put those conversations into the convo list window
	      reRender()
      }case l: Long => {
         var currUser = User.currentUser.get
    	  currUser.currentConversation(l)
    	  currUser.save
        reRender()
      }
      
  }

  def render = " option " #> conversations.map{ c => " option * " #> (c.topic.get) & "* [value]" #> c.id}
}