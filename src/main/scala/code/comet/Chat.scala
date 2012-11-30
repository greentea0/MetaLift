package code
package comet
import net.liftweb._
import http._
import util._
import Helpers._
import java.sql.Date
import code.model._
import code.model.Message
import java.util.Calendar
import net.liftweb.mapper._
import net.liftweb.mapper.MappedLongIndex
import net.liftweb.http.js.JE
/**
 * The screen real estate on the browser will be represented
 * by this component.  When the component changes on the server
 * the changes are automatically reflected in the browser.
 */
class Chat extends CometActor with CometListener {
  private var msgs: List[String] = List() // private state
  /**
   * When the component is instantiated, register as
   * a listener with the ChatServer
   */
  def registerWith = ChatServer
  /**
   * The CometActor is an Actor, so it processes messages.
   * In this case, we're listening for Vector[String],
   * and when we get one, update our private state
   * and reRender() the component.  reRender() will
   * cause changes to be sent to the browser.
   */
  override def lowPriority = {
  	case s: String => {
	          // grab all the messages for the current user for their current conversation
  		try {
  		    val convoId = User.currentUser.get.currentConversation.get
  		    val conversation: Conversation = Conversation.findByKey(convoId).get
  			val messages: List[Message] = conversation.messages.toList
		    
		      
		          // put those messages into the chat window
		    msgs = messages.map(( m : Message ) =>
		    	m.sender.obj.get.firstName.get  
		        +" " + 
		        m.sender.obj.get.lastName.get
		        +" : "+
		        m.payload.get)
		} catch  {
			case nsee : NoSuchElementException => println("No such element : "+nsee.getMessage());
		}
	}
   
    
	reRender()
    
  }
  /**s
   * Put the messages in the li elements and clear
   * any elements that have the clearable class.
   */
  def render = {
    "li *" #> msgs & ClearClearable

    }
}