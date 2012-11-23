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
class Conversations extends CometActor with CometListener {
  private var convos: List[String] = List() // private state
  /**
   * When the component is instantiated, register as
   * a listener with the ConvoServer
   */
  def registerWith = ConvoServer
  /**
   * The CometActor is an Actor, so it processes messages.
   * In this case, we're listening for Vector[String],
   * and when we get one, update our private state
   * and reRender() the component.  reRender() will
   * cause changes to be sent to the browser.
   */
  override def lowPriority = {
    case v: Vector[String] => 
      if ( !v.last.isEmpty()){
          // grab all the message sfor the current user for their current conversation
		  val conversations = Conversation.findAll(In(Conversation.id, History.user, By(History.user,
	          User.currentUser.openOrThrowException("Unable to get user when I am trying to pull for all the messages!").id)))
	      
	          // put those messages into the chat window
	           convos = conversations.map(( c : Conversation ) =>
	           c.topic.get)
      }
      reRender()
  }
  /**s
   * Put the messages in the li elements and clear
   * any elements that have the clearable class.
   */
  def render = "li *" #> convos & ClearClearable
}