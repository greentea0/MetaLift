package code
package snippet
import net.liftweb._
import http._
import js._
import JsCmds._
import JE._
import comet.ChatServer
import code.model.Message
import java.util.Calendar
import code.model.User
/**
 * A snippet transforms input to output... it transforms
 * templates to dynamic content.  Lift's templates can invoke
 * snippets and the snippets are resolved in many different
 * ways including "by convention".  The snippet package
 * has named snippets and those snippets can be classes
 * that are instantiated when invoked or they can be
 * objects, singletons.  Singletons are useful if there's
 * no explicit state managed in the snippet.
 */
object ChatIn {
  /**
   * The render method in this case returns a function
   * that transforms NodeSeq => NodeSeq.  In this case,
   * the function transforms a form input element by attaching
   * behavior to the input.  The behavior is to send a message
   * to the ChatServer and then returns JavaScript which
   * clears the input.
   */
  def render = SHtml.onSubmit(s => {
    
    var msg = Message.create
     if ( !s.trim.isEmpty() ){
	      msg.payload(s)
	      msg.dateSent(Calendar.getInstance().getTime())
	      msg.conversation(1)
	      msg.sender(User.currentUser)
	      msg.save
	      
	      ChatServer ! s
	      
  	}
    SetValById("chat_in", "")
  })
}