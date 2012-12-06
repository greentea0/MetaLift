package code
package snippet


import code.model.FriendsList
import net.liftweb.util._
import net.liftweb.common._
import code.lib._
import Helpers._
import code.model.FriendsList
import net.liftweb.mapper.By
import code.model._

class FriendsToAccept {
	
  def render = {
	  
	//val li : List[User] = User.currentUser.get.friends.get
	
	val invites: List[Invitation] = Invitation.findAll(By(Invitation.friendID, User.currentUser.get.id.get ))
	
	val requesters = invites.map( i => User.find(By( User.id, i.requesterID.get)).get)
   
	val list = requesters.map( (user : User ) => (user.firstName.get + " " + user.lastName.get, user.id.get ) )

    
    " option " #> requesters.map{r => " option * " #> (r.firstName.get + " " + r.lastName.get) & "* [value]" #> r.id}
	}
 
}