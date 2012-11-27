package code.comet

import net.liftweb.util.Schedule
import net.liftweb.actor.LiftActor
import net.liftweb.util.Helpers._

object TrendServer extends LiftActor {
  
  case class DoIt()
  case class Stop()
  
  private var stopped = false
  
   def messageHandler = {  
     case DoIt => 
       if (!stopped) 
         println("Calclating some trend")
    	   Schedule.schedule(this, DoIt, 5 seconds)//10 minutes
    	   
     
     case Stop =>
       stopped = true
       println("Stopping the trending calculation server")
   }
  
  
}