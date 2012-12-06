package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._
import common._
import http._
import js.jquery.JQueryArtifacts
import sitemap._
import Loc._
import mapper._
import code.model._
import net.liftmodules.JQueryModule
import net.liftweb.widgets.menu._
import net.liftweb.widgets.flot._
import java.sql.Connection
import java.sql.DriverManager
import code.comet.TrendServer

object DBVendor extends ConnectionManager with Logger {
  def newConnection(name: ConnectionIdentifier): Box[Connection] = {
    try {
      Class.forName("com.mysql.jdbc.Driver")
     
      val jdbcurl= (Props.get("db.url") openOr "") +
        "?user=" + (Props.get("db.user") openOr "") +
        "&password=" + (Props.get("db.password") openOr "") +
        "&" + Props.get("additionalurlparam").openOr("")
      debug(jdbcurl)

      val dm = DriverManager.getConnection(jdbcurl)
      Full(dm)
    } catch {
      case e : Exception => e.printStackTrace; Empty
    }
  }
  def releaseConnection(conn: Connection) {conn.close}
}

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor = 
  new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
           Props.get("db.url") openOr 
           "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
           Props.get("db.user"), Props.get("db.password"))

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(DefaultConnectionIdentifier, DBVendor)
	  net.liftweb.widgets.autocomplete.AutoComplete.init
    }

    // Use Lift's Mapper ORM to populate the database
    // you don't need to use Mapper to use Lift... use
    // any ORM you want
Schemifier.schemify(true, Schemifier.infoF _, User, Conversation, Friendship, Invitation, ConversationParticipants, FriendsList, Trend, TrendWord, IgnoredList, Message)

    // where to search snippet
    LiftRules.addToPackages("code")

	// check if the user is logged in
    
	val loggedIn = If(User.loggedIn_? _, RedirectResponse("signinsignup"))

    // Build SiteMap
      def sitemap = SiteMap(
    	 Menu.i("Home") / "index" >> loggedIn, 
    	
         Menu.i("Chat") / "chat" >> loggedIn submenus(
        		 Menu.i("New Conversation") / "newconversation" >> loggedIn,
        		 Menu.i("Change Conversation") / "conversations" >> loggedIn,
        		 Menu.i("Conver_info") / "conver_info" >> loggedIn >> Hidden),
		 Menu.i("Friends") / "friends" >> loggedIn submenus(
			 Menu.i("Add friends") /"CreateFriendRequest" >> loggedIn,
			 Menu.i("Remove Friend") / "RemoveFriend" >> loggedIn,
			 Menu.i("Requests") / "AcceptFriendRequest" >> loggedIn),
			 
		 Menu.i("Trends") / "trends" >> loggedIn submenus(
		     Menu.i("Add trend") /"addtrend" >> loggedIn),
		 
		 //Menu.i("Account")  /"account" >> loggedIn >> submenus(User.menus),
		 
		 Menu.i("Send Request") / "ConfirmCreateFriendRequest" >> loggedIn >> Hidden, 
		 Menu.i("Comfirm friendship") / "ConfirmAcceptFriendRequest" >> loggedIn >> Hidden,		 
		 Menu.i("Friend removed") / "RemoveFriendConfirmation" >> loggedIn >> Hidden,
		 
		 Menu.i("Sign In/Sign Up") / "signinsignup" >> Hidden
    )
        

    
    //def sitemapMutators = Conversation.sitemapMutator
    def sitemapMutators = User.sitemapMutator

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMapFunc(() => sitemapMutators(sitemap)) //sitemapMutators()

    //Init the jQuery module, see http://liftweb.net/jquery for more information.
    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery=JQueryModule.JQuery172
    JQueryModule.init()

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
   
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)
   
    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // What is the function to test if a user is logged in?
    LiftRules.loggedInTest = Full(() => User.loggedIn_?)
    // on startup we want to start calculating trends every hour
    // we also need to make sure we stop this process when the server goes offline
     TrendServer ! TrendServer.DoIt
    LiftRules.unloadHooks.append( () => TrendServer ! TrendServer.Stop )
    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))    

      Flot.init
      MenuWidget.init
    // Make a transaction span the whole HTTP request
    S.addAround(DB.buildLoanWrapper)
  }
}
