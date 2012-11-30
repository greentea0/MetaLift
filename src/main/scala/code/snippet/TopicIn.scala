package code
package snippet
import net.liftweb._
import http._
import js._
import JsCmds._
import JE._
import comet.ConvoServer
import code.model._
import java.util.Calendar
import code.model.User
import code.comet.ConfirmedFriendsForConversation


/*
 * A snippet that will create a conversation with selected users and topic entered
 * When click on create, it will create the conversation
*/
 
object TopicIn {
 /*
   * The render method in this case returns a function
   * that transforms NodeSeq => NodeSeq.  In this case,
   * the function transforms a form input element by attaching
   * behavior to the input.  The behavior is to send a message
   * to the ChatServer and then returns JavaScript which
   * clears the input.
*/

  private def createConversation(topic: String) = {

		  var newConvo: Conversation =  Conversation.create

		  newConvo.topic(topic.trim())
		  newConvo.startedBy(User.currentUser)
		  newConvo.startedAt(Calendar.getInstance().getTime())
		  newConvo.participants += User.currentUser.get // no change

		  newConvo.save
		  
		  newConvo
		 
  }
  
  private def addParticipants(convo: Conversation, users: List[User]) = {
    	  users.foreach(user => convo.participants += user)
		  convo.save
  }
  
  def render = SHtml.onSubmit(topic => {
     var usersToAdd: List[User] = ConfirmedFriendsForConversation.l
     
     if (!topic.trim.isEmpty()){ 
		  var conversation: Conversation =  createConversation(topic)
		  addParticipants(conversation, usersToAdd)
     }
     
     ConvoServer ! topic
     
	 SetValById("topic_in", "")
  })
}