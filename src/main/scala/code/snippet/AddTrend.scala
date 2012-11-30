package code
package snippet
import net.liftweb._
import http._
import js._
import JsCmds._
import JE._
import comet.UserServer
import net.liftweb.util.Helpers._
import comet.TrendWordServer
import model.TrendWord
import net.liftweb.mapper._
import java.util.Calendar


/*
 * A snippet that search on user's name
 * When click on search, it will look into the user's friend
 * Then return the result in another form
*/
object AddTrend {
/* 
   * The rende
   * r method in this case returns a function
   * that transforms NodeSeq => NodeSeq.  In this case,
   * the function transforms a form input element by attaching
   * behavior to the input.  The behavior is to send a message
   * to the ChatServer and then returns JavaScript which
   * clears the input.
*/
  def render = SHtml.onSubmit(s => {
        println(" This is s in AddTrend " + s)
        
         val words = TrendWord.findAll( By( TrendWord.word, s))
        if ( words.size == 0 ){
          val newWord = TrendWord.create
  		   newWord.word( s)
  		   newWord.introduced( Calendar.getInstance.getTime )
  		   newWord.save
        //TrendWordViewer.trendWords = words
  		}
        
	    TrendWordServer ! s
	}
  )
}