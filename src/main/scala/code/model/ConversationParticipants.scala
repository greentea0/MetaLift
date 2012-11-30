package code {
package model {

import net.liftweb.mapper._

	object ConversationParticipants extends ConversationParticipants with LongKeyedMetaMapper[ConversationParticipants]

	class ConversationParticipants extends LongKeyedMapper[ConversationParticipants] with IdPK {
	  def getSingleton = ConversationParticipants
	  
	  object conversation extends MappedLongForeignKey(this, Conversation)
	  object participant extends MappedLongForeignKey(this, User)
	}

}
}