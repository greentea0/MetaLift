package code
package model

import net.liftweb.mapper.LongKeyedMetaMapper
import net.liftweb.mapper.LongKeyedMapper
import net.liftweb.mapper._


class Conversation  extends LongKeyedMapper[Conversation] {
   def getSingleton = Conversation
	
  def primaryKeyField = id
  object id extends MappedLongIndex(this)
  object startedBy extends MappedLongForeignKey(this, User)
  object startedAt extends MappedDateTime( this )
}

object Conversation extends Conversation with LongKeyedMetaMapper[Conversation] {
  
}