package code
package snippet


import code.model.FriendsList
import net.liftweb.util._
import net.liftweb.common._
import code.lib._
import Helpers._
import code.model.FriendsList
import net.liftweb.mapper.By
import code.model.User

class FriendsToAccept {
	def render = {
	  val list = List()
	  
	val li = FriendsList.findAll(By( FriendsList.friendID, User.currentUser.get.id.get))
	val requestors = li.map( i => User.find(By( User.id, i.requesterID.get)).get)
    val names = requestors.map( (user : User ) => user.firstName.get+" " +user.lastName.get)
    val ids = li.map( x => x.id.get)
    val t = ids.zip(names)
    " option " #> t.map
    { 
      d => " option * " #> d._2 & "* [value]" #> d._1}
	}
}