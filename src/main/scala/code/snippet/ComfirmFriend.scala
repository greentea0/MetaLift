package code.snippet

import net.liftweb._
import http._
import js._
import JsCmds._
import JE._
import net.liftweb.mapper._
import net.liftweb.mapper.MappedLongIndex
import code.model.User
import code.model.FriendsList
import java.util.Calendar

object ComfirmFriend {
  
//  object requesterID extends MappedLongForeignKey(this, User)
//  object friendID extends MappedLongForeignKey(this, User)
//  object joined extends MappedDateTime( this )
	def render = SHtml.onSubmit(s => {
	  
	 
	  var newFriend = FriendsList.find( By(FriendsList.requesterID, s.toLong)).get
	  newFriend.joined( Calendar.getInstance().getTime())
	  

	  })

}