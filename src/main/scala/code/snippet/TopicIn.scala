package code
package snippet
import net.liftweb._
import http._
import js._
import JsCmds._
import JE._
import comet.ConvoServer
import code.model.History
import code.model.Conversation
import java.util.Calendar


import code.model.User

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
  def render = SHtml.onSubmit(topic => {
        
     if ( !topic.trim.isEmpty() ){
		  		 
		  //var newConvo: Conversation =  Conversation.create.topic(topic).startedBy(User.currentUser)
	      
	      ConvoServer ! topic
  	}
     
    SetValById("topic_in", "")
  })
}