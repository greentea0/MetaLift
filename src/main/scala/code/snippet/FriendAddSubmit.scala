package code.snippet

import net.liftweb._
import http._
import js._
import JsCmds._
import JE._

import code.model._


object FriendAddSubmit {
  
	def render = SHtml.onSubmit(s => {
	
	
	  var i = Invitation.create
	  i.requesterID(User.currentUser.get.id.get)
	  i.friendID(s.toLong)
	  i.save
	})
	
}