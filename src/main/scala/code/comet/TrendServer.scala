package code.comet

import net.liftweb.util.Schedule
import net.liftweb.actor.LiftActor
import net.liftweb.util.Helpers._
import code.model._
import java.util.Calendar
import net.liftweb.mapper._
import java.util.Date


object TrendServer extends LiftActor {
  
  val HOURLY:Int = 0
  val DAILY:Int = 1
  val WEEKLY:Int = 2
  val MONTHLY:Int = 3
  val ALLTIME:Int = 4
  
  case class DoIt()
  case class Stop()
  
  private var stopped = false
  
   def messageHandler = {  
     case DoIt => 
       /* Ramblings
        * 1. want to collect data for the user, and refresh it once an hour
        * 2. want to collect data for a conversation refresh it once an hour
        * 3. need the ability to do custom queries
        * 4. hourly, daily, weekly, monthly stats ( calculated every hour, i know this inefficient
        * 5. custom trends? optional?
        */
       if (!stopped){	
           createTrends
           println("finished!")
           Schedule.schedule(this, DoIt, 20 seconds)// currently runs every 20 seconds
           //10 minutes - 
       }
     
     case Stop =>
       stopped = true
       println("Stopping the trending calculation server")
  }
  
  def createTrends = {
    // first remove all of the rows , i know this step sucks
    Trend.findAll().foreach( trendToRemove => trendToRemove.delete_!  )
    //val words = TrendWord.findAll().map(( keyword : TrendWord ) => keyword.word.get)
    val words = List("blackberry", "iphone", "android")
    createConversationTrends( words )
    createUserTrends( words )
    
     
  }
  
  
  def createConversationTrends( words : List[String]) = {
    createHourlyConversationTrends( words )
    createDailyConversationTrends( words )
    createWeeklyConversationTrends( words )
    createMonthlyConversationTrends( words )
    createAllTimeConversationTrends( words )
  }
  
   def createUserTrends( words : List[String]) = {
    createHourlyUserTrends( words )
    createDailyUserTrends( words )
    createWeeklyUserTrends( words )
    createMonthlyUserTrends( words )
    createAllTimeUserTrends( words )
  }
   
   /*
    * 
    *   def primaryKeyField = id
  object id extends MappedLongIndex(this)
  object user extends MappedLongForeignKey( this , User )
  object conversation extends MappedLongForeignKey( this, Conversation )
  object word extends MappedLongForeignKey( this, TrendWord )
  object occurrence extends MappedDateTime( this )
  object reportType extends MappedString(this, 140)
  
  *
  */
	def userTrend(user:User,timeType:Int,words:List[String]) = {
	    
		val trends = words.zip(words.map(( currentKeyword : String ) => 
			Message.findAll(By( Message.sender, user.id.get), By_>=( Message.dateSent,  getTimeOffset( timeType )  )).filter( ( currentMessage : Message ) => 
				currentMessage.payload.get.split(" " ).contains(currentKeyword)).size))
				
		//println( "user trend - " + user.firstName + " " + user.lastName.get + " [id] -> " + user.id.get + " Time type = "+  timeType + "\nTrends:\n ")
		//trends.foreach( trend => println(  trend ))
		val Trend = Unit
		trends.foreach( trend =>
		 // println( trend._1 + "- " + trend._2 )
		  createUserTrendInDB( user,  TrendWord.find(By(TrendWord.word, trend._1)).get.id.get, trend._2, timeType)
	    )
  	}
	
	def createConveresationTrendInDB( conversation:Conversation, wordVal : Long, occurrence : Int, reportType : Int) = {
	  		    val trend = Trend.create
		    trend.conversation( conversation)
		    trend.word( wordVal )
		    trend.occurrence( occurrence )
		    trend.reportType( reportType )
		    trend.save
	}
	def createUserTrendInDB( user:User, wordVal : Long, occurrence : Int, reportType : Int) = {
	  		    val trend = Trend.create
		    trend.user( user)
		    trend.word( wordVal )
		    trend.occurrence( occurrence )
		    trend.reportType( reportType )
		    trend.save
	}
	def conversationTrend(conversation:Conversation,timeType:Int,words:List[String]) = {
	  val trends = words.zip(words.map(( currentKeyword : String ) => 
			Message.findAll(By( Message.conversation, conversation.id.get), By_>=( Message.dateSent, getTimeOffset( timeType )  )).filter( ( currentMessage : Message ) => 
				currentMessage.payload.get.split(" " ).contains(currentKeyword)).size))
	//	println("conversation trend - "+conversation.topic.get + " [id] -> " + conversation.id.get + " Time type = "+  timeType + " \nTrends:\n" )
	//	trends.foreach( trend => println( trend ))
				trends.foreach( trend =>
		 createConveresationTrendInDB( conversation,  TrendWord.find(By(TrendWord.word, trend._1)).get.id.get, trend._2, timeType)
		//		 println( trend._1 + "- " + trend._2 )
	    )
	}
	
	def getTimeOffset( timeVal : Int ) : java.util.Date = {
    var tempTime = Calendar.getInstance
    if ( timeVal == HOURLY ){
      var tempVar = tempTime.get(Calendar.HOUR) - 1
      tempTime.set(Calendar.HOUR, tempVar)
    } else if ( timeVal == DAILY ){
       var tempVar = tempTime.get(Calendar.DAY_OF_YEAR) - 1
      tempTime.set(Calendar.DAY_OF_YEAR, tempVar)
    } else if ( timeVal == WEEKLY ){
       var tempVar = tempTime.get(Calendar.DAY_OF_YEAR) - 7
      tempTime.set(Calendar.DAY_OF_YEAR, tempVar)
    } else if ( timeVal == MONTHLY ){
       var tempVar = tempTime.get(Calendar.DAY_OF_YEAR) - 30
      tempTime.set(Calendar.DAY_OF_YEAR, tempVar)
    } else if ( timeVal == ALLTIME ){
      tempTime.set(Calendar.YEAR, 1970)
    }
    tempTime.getTime
  }
	
	// Conversation Trends
	def createHourlyConversationTrends( words : List[String] )  = {
      Conversation.findAll().foreach( conversation => conversationTrend( conversation, HOURLY, words ))
    }
	def createDailyConversationTrends( words : List[String] ) = {
	  Conversation.findAll().foreach( conversation => conversationTrend( conversation, DAILY, words ))
	}
  	def createWeeklyConversationTrends( words : List[String] ) = {
  	    Conversation.findAll().foreach( conversation => conversationTrend( conversation, WEEKLY, words ))
  	}
	def createMonthlyConversationTrends( words : List[String] ) = {
		Conversation.findAll().foreach( conversation => conversationTrend( conversation,MONTHLY, words ))
	}
	def createAllTimeConversationTrends( words : List[String] ) = {
		Conversation.findAll().foreach( conversation => conversationTrend( conversation, ALLTIME, words ))
	}
	
	//User Trends
	def createHourlyUserTrends( words : List[String] ) = {
		User.findAll().foreach( user => userTrend(user,HOURLY,words))
	}
	def createDailyUserTrends( words : List[String] ) = {
	  User.findAll().foreach( user => userTrend(user,DAILY,words))
	}
	def createWeeklyUserTrends( words : List[String] ) = {
		User.findAll().foreach( user => userTrend(user,WEEKLY,words))
	}
	def createMonthlyUserTrends( words : List[String] ) = {
		User.findAll().foreach( user => userTrend(user,MONTHLY,words))
	}
	def createAllTimeUserTrends( words : List[String] ) = {
		User.findAll().foreach( user => userTrend(user,ALLTIME,words))
	}
}