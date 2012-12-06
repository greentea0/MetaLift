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
 
object ChangeTopic {

  
  private def changeConversation(id: Long) = {
    ConvoServer ! id
  }
  
  def render = SHtml.onSubmit(convoId => {
     
     if (!convoId.trim.isEmpty()){ 
		  changeConversation(convoId.toLong)
     }     
    
  })
}