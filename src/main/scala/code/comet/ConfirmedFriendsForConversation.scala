package code.comet

import net.liftweb.util.Schedule
import net.liftweb.actor.LiftActor
import net.liftweb.util.Helpers._
import code.model._
import net.liftweb.mapper.By
import net.liftweb.http.CometActor
import net.liftweb.http.CometListener

class ConfirmedFriendsForConversation extends CometActor with CometListener {
   var confirmedConversationFriends: List[User] = List() // private state

    def registerWith = ConfirmFriendsConvServer
    
    override def lowPriority = {
    	case s : Long =>
    	    var user = User.find(By( User.id, s)).get
    	    if ( !confirmedConversationFriends.contains( user )){
    	      confirmedConversationFriends = confirmedConversationFriends.++(List(user))
    	    }
    	    confirmedConversationFriends.foreach( user  => println(user.firstName.get+" " +user.lastName.get))
    	    reRender()
   }
  
  def render = {
    " option " #> confirmedConversationFriends.map
    { d => " option * " #> (d.firstName + "  " 
        + d.lastName) & "* [value]" #> d.id}

 }
}