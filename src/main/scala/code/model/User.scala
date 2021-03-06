package code
package model

import net.liftweb.mapper._
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.sitemap.Loc._

/**
 * The singleton that has methods for accessing the database
 */
object User extends User with MetaMegaProtoUser[User] {
  

  override def dbTableName = "users" // define the DB table name
  override def screenWrap = Full(<lift:surround with="default" at="content">
			       <lift:bind /></lift:surround>)
  // define the order fields will appear in forms and output
  override def fieldOrder = List(id, firstName, lastName, email, password, nickname, textArea)
  override def signupFields = super.signupFields.filter(_ != locale).filter(_ !=timezone)
  // comment this line out to require email validations
  override def skipEmailValidation = true

  override def homePage = if ( loggedIn_? ) {
    "/index"
  } else { "/chat" }

}

/**
 * An O-R mapped "User" class that includes first name, last name, password and we add a "Personal Essay" to it
 */
class User extends MegaProtoUser[User] with ManyToMany {
  def getSingleton = User // what's the "meta" server
  
  object friendsList extends MappedLongForeignKey( this, FriendsList )
  object currentConversation extends MappedLongForeignKey( this, Conversation )
  object dateRegistered extends MappedDateTime( this )
  object dateLoggedOn extends MappedDateTime( this )
  object dateLoggedOff extends MappedDateTime( this )
  object status extends MappedInt( this )
  object nickname extends MappedString( this, 140 )
  object conversations extends MappedManyToMany( ConversationParticipants, ConversationParticipants.participant, ConversationParticipants.conversation, Conversation)
  object friends extends MappedManyToMany( Friendship, Friendship.user1, Friendship.user2, User)
  
  // define an additional field for a personal essay
  object textArea extends MappedTextarea(this, 2048) {
    override def textareaRows  = 10
    override def textareaCols = 50
    override def displayName = "Personal Essay"
  }
  
}

