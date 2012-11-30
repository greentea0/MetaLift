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
     
     if (!s.trim.isEmpty()){
          var msg = Message.create
          msg.payload(s.trim())
	      msg.dateSent( Calendar.getInstance().getTime())
	      msg.conversation(User.currentUser.get.currentConversation.get)
	      msg.sender(User.currentUser.get.id.get)
	      msg.save
	      
	      Thread.sleep(500)
	      
  	}
    
    ChatServer ! s
    
    SetValById("chat_in", "")
        
  })
}