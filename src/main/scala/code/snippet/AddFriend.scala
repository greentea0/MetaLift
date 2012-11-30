package code.snippet

import net.liftweb._
import http._
import js._
import JsCmds._
import JE._

import code.model.User
import code.model.FriendsList

object AddFriend {
  
//  object requesterID extends MappedLongForeignKey(this, User)
//  object friendID extends MappedLongForeignKey(this, User)
//  object joined extends MappedDateTime( this )
	def render = SHtml.onSubmit(s => {
	  var f = FriendsList.create
	  f.requesterID(User.currentUser.get.id.get)
	  f.friendID(s.toLong)
	  f.save
	 
	   
	   
	  })

}