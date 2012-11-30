package code
package snippet
import net.liftweb._
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds.SetValById
import code.model.User
import net.liftweb.mapper.By
import net.liftweb.mapper.In
import code.comet.ConfirmedFriendsForConversation
import code.model._
import code.comet.ConvoServer
import code.comet.ConfirmFriendsConvServer

class AddedPeopleToConversation {
 def render = SHtml.onSubmitList(s => {
	 
	 // we are only going to push the first one
  // var users = s.map( ( id : String ) => User.find( By(User.id, id.toLong )).get)
    ConfirmFriendsConvServer ! s.head.toLong
    SetValById("addUsers", "")
  }); 
  
}

// def render = ".userselected option *" #> s
