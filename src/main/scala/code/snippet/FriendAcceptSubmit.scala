package code
package snippet
import net.liftweb._
import http._
import js._
import JsCmds._
import JE._
import net.liftweb.util.Helpers._
import code.model.FriendsList
import net.liftweb.mapper.By
import code.model._
import net.liftweb.mapper.ByList
import java.util.Calendar


/*
 * A snippet that search on user's name
 * When click on search, it will look into the user's friend
 * Then return the result in another form
*/
class FriendAcceptSubmit {
/* 
   * The render method in this case returns a function
   * that transforms NodeSeq => NodeSeq.  In this case,
   * the function transforms a form input element by attaching
   * behavior to the input.  The behavior is to send a message
   * to the ChatServer and then returns JavaScript which
   * clears the input.
*/
 // def buttonBind = "#button [onclick]" #> (RedirectTo("ConfirmAcceptFriendRequest.html")).toJsCmd
def render = SHtml.onSubmit(s => {
  

	  var invite = Invitation.find(By(Invitation.requesterID,s.toLong), By(Invitation.friendID, User.currentUser.get.id.get)).get
	  
	  var theirFriendship = Friendship.create	
	  
	  theirFriendship.user1(invite.requesterID.get)
	  theirFriendship.user2(invite.friendID.get)
	  theirFriendship.friended(Calendar.getInstance().getTime())
	  theirFriendship.save
	  
	  var yourFriendship = Friendship.create	
	  
	  yourFriendship.user1(invite.friendID.get)
	  yourFriendship.user2(invite.requesterID.get)
	  yourFriendship.friended(Calendar.getInstance().getTime())
	  yourFriendship.save
	  
	  invite.delete_!
})
}