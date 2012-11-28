package code.comet

import net.liftweb.util.Schedule
import net.liftweb.actor.LiftActor
import net.liftweb.util.Helpers._
import code.model._
import net.liftweb.mapper.By

object TrendServer extends LiftActor {
  
  case class DoIt()
  case class Stop()
  
  private var stopped = false
  
   def messageHandler = {  
     case DoIt => 
       if (!stopped) 
          println("Calclating some trend")
         val keywrd = "blackberry"
         //val keywords = ("blackberry", "iphone", "android")
         val messages = Message.findAll(By( Message.conversation, 1))
         var g = null
         var a = messages.filter( (m :Message) => m.payload.contains( keywrd)).size
         writeNumBlackBerryOccurence( a )
    	 
         
         Schedule.schedule(this, DoIt, 20 seconds)//10 minutes
    	   
     
     case Stop =>
       stopped = true
       println("Stopping the trending calculation server")
   }
  
  def writeNumBlackBerryOccurence( c : Int ) = {
    println( c +" references to blackberry")
  }
}