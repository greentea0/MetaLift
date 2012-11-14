package code
package model

import net.liftweb.mapper.LongKeyedMetaMapper
import net.liftweb.mapper.LongKeyedMapper
import net.liftweb.mapper._


class History  extends LongKeyedMapper[History] {
   def getSingleton = History
	
  def primaryKeyField = id
  object id extends MappedLongIndex(this)
  object conversationID extends MappedLongForeignKey(this, Conversation)
  object joined extends MappedDateTime( this )
}

object History extends History with LongKeyedMetaMapper[History] {
  
}