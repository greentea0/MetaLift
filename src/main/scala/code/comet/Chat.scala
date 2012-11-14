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
    case v: List[String] => 
      var msg = Message.create
      msg.payload.:=(v.last)
      msg.dateSent.:=( Calendar.getInstance().getTime())
      msg.conversationID.:=(1)
      msg.sender.:=(User.currentUser.open_!.id)
      msg.save
      val listOfMessages = Message.findAll
      msgs = listOfMessages.map((i: Message) => i.payload.asString )
      reRender()
  }
  /**
   * Put the messages in the li elements and clear
   * any elements that have the clearable class.
   */
  def render = "li *" #> msgs & ClearClearable
}