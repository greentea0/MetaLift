package code.snippet

import net.liftweb._
import http._
import js._
import JsCmds._
import JE._
import net.liftweb.mapper._
import net.liftweb.mapper.MappedLongIndex
import code.model._
import java.util.Calendar

object ComfirmFriend {
  
//  object requesterID extends MappedLongForeignKey(this, User)
//  object friendID extends MappedLongForeignKey(this, User)
//  object joined extends MappedDateTime( this )
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