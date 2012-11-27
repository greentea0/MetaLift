package code.snippet;
import _root_.scala.xml._
import _root_.net.liftweb._
import http._
import S._
import SHtml._
import common._
import util._
import Helpers._
import js._
import JsCmds._
import code.model._
import net.liftweb.mapper._
import net.liftweb.widgets.autocomplete.AutoComplete

class FindSelectUser {
	  	  
	  var users: List[User] = List()
	  var someuser = ""

  // bind the view to the dynamic HTML
  def show(xhtml: Group): NodeSeq = {
		 		
		 bind("ajax", xhtml,
         "search" -> AutoComplete( "", buildQuery _, selection => processResult(selection)),
         "submit" -> submit(?("Add"), () =>  {S.notice("Added User: " + someuser)})
         )
                             //redirectTo("/")
  }
	  
  private def findUsersBy(name: String): List[User] = User.findAll(Like( User.firstName, (name + "%")))
  
  
  private def buildQuery(current: String, limit: Int): Seq[String] = {
    val usersFound = findUsersBy(current)
    usersFound.map(( user : User ) => (user.firstName + " " + user.lastName))
  }
  
  private def processResult(value: String) = {
    val firstname = value.substring(0, value.indexOf(" "))
    val lastname =  value.substring((value.indexOf(" ")+ 1), (value.length() -1 ))
    users :+ User.findAll(By(User.firstName, firstname)).head
  }
  
}
