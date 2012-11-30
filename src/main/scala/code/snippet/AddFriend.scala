package code.snippet

import net.liftweb._
import http._
import js._
import JsCmds._
import JE._

import code.model._

object AddFriend {
  
//  object requesterID extends MappedLongForeignKey(this, User)
//  object friendID extends MappedLongForeignKey(this, User)
//  object joined extends MappedDateTime( this )
	def render = SHtml.onSubmit(s => {
	  var i = Invitation.create
	  i.requesterID(User.currentUser.get.id.get)
	  i.friendID(s.toLong)
	  i.save
	 
	   
	  })

}