package code
package snippet
import net.liftweb._
import http._
import js._
import JsCmds._
import JE._



import code.model.User

/*
 * A snippet that search on user's name
 * When click on search, it will look into the user's friend
 * Then return the result in another form
 
object SearchUserName {
 
   * The render method in this case returns a function
   * that transforms NodeSeq => NodeSeq.  In this case,
   * the function transforms a form input element by attaching
   * behavior to the input.  The behavior is to send a message
   * to the ChatServer and then returns JavaScript which
   * clears the input.

  def render = SHtml.onSubmit(s => {
    
    var msg = Message.create
     if ( !s.trim.isEmpty() ){
	      msg.payload.:=(s)
	      msg.dateSent.:=( Calendar.getInstance().getTime())
	      msg.conversationID.:=(1)
	      msg.sender.:= (User.
	          currentUser.
	          	openOrThrowException(
	          	    "This snippet is used on pages where the user is logged in, you are attempting to write a message but the user does not exist").id)
	      msg.save
	      
	      ChatServer ! s
  	}
    SetValById("chat_in", "")
  })
}
*/