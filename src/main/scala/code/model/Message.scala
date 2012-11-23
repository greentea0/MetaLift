package code
package model

import net.liftweb.mapper._


class Message extends LongKeyedMapper[Message] {
  def getSingleton = Message
	
  def primaryKeyField = id
  object id extends MappedLongIndex(this)
  object conversation extends MappedLongForeignKey( this , Conversation )
  object sender extends MappedLongForeignKey( this, User )
  object dateSent extends MappedDateTime( this )
  object payload extends MappedString(this, 140)
}


object Message extends Message with LongKeyedMetaMapper[Message] {
  
}