package code
package snippet
import net.liftweb._
import http._
import js._
import JsCmds._
import JE._

import net.liftweb.util.Helpers._


/*
 * A snippet that search on user's name
 * When click on search, it will look into the user's friend
 * Then return the result in another form
*/
class Ok {
/* 
   * The render method in this case returns a function
   * that transforms NodeSeq => NodeSeq.  In this case,
   * the function transforms a form input element by attaching
   * behavior to the input.  The behavior is to send a message
   * to the ChatServer and then returns JavaScript which
   * clears the input.
*/
  def buttonBind = "#button [onclick]" #> (RedirectTo("index.html")).toJsCmd
}