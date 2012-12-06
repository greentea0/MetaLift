package code
package snippet

import net.liftweb.util._
import net.liftweb.common._
import code.lib._
import Helpers._
import code.model.FriendsList
import net.liftweb.mapper.By
import code.model.User

class FriendshipsToRemove {
	def render = {
	  var friendshipsRequested = FriendsList.findAll( By( FriendsList.requesterID, User.currentUser.get.id.get))
      var friendshipsAccepted = FriendsList.findAll( By( FriendsList.friendID, User.currentUser.get.id.get))
      val friendships = friendshipsRequested.++(friendshipsAccepted)
      
      val li = friendships.map( ( friendship : FriendsList ) => 
        if( User.currentUser.get.id.get == friendship.id.get){
            friendship.requesterID.get
        } else {
          friendship.id.get
        }
      )
      val users = li.map( ( i : Long ) =>User.find( By ( User.id, i)).get)
      val names = users.map( ( user : User ) => user.firstName.get + " "  + user.lastName.get)
      val list = li.zip( names )
	  " option " #> list.map
    { d => " option * " #> d._2 & "* [value]" #> d._1}
  }
}