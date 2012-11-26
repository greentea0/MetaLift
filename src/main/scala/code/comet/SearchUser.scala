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
/**
 * The screen real estate on the browser will be represented
 * by this component.  When the component changes on the server
 * the changes are automatically reflected in the browser.
 */
class SearchUser extends CometActor with CometListener {
  private var usersFound: List[String] = List() // private state
  /**
   * When the component is instantiated, register as
   * a listener with the ChatServer
   */
  def registerWith = UserServer
  /**
   * The CometActor is an Actor, so it processes messages.
   * In this case, we're listening for Vector[String],
   * and when we get one, update our private state
   * and reRender() the component.  reRender() will
   * cause changes to be sent to the browser.
   */
  override def lowPriority = {
    case s:String => 
      {
        val users: List[User] = User.findAll(By( User.firstName, s))
	    usersFound = users.map( ( user : User ) => user.firstName + " " + user.lastName)
      }
      
      reRender()
  }
  /**s
   * Put the messages in the li elements and clear
   * any elements that have the clearable class.
   */
  def render = "option *" #> usersFound & ClearClearable
}