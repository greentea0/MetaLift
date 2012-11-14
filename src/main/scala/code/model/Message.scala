package code
package model

import net.liftweb.mapper.LongKeyedMetaMapper
import net.liftweb.mapper.LongKeyedMapper
import net.liftweb.mapper._


class Message extends LongKeyedMapper[Message] {
   def getSingleton = Message
	
  def primaryKeyField = id
  object id extends MappedLongIndex(this)
  object conversationID extends MappedLongForeignKey( this , Conversation )
  object sender extends MappedLongForeignKey( this, User )
  object dateSent extends MappedDateTime( this )
  object payload extends MappedString(this, 140)
}

object Message extends Message with LongKeyedMetaMapper[Message] {
  
}