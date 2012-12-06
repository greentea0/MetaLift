package code
package model

import net.liftweb.mapper.LongKeyedMetaMapper
import net.liftweb.mapper.LongKeyedMapper
import net.liftweb.mapper._


class Invitation  extends LongKeyedMapper[Invitation] {
   def getSingleton = Invitation
	
  def primaryKeyField = id
  object id extends MappedLongIndex(this)
  object requesterID extends MappedLongForeignKey(this, User)
  object friendID extends MappedLongForeignKey(this, User)
  object sent extends MappedDateTime( this )
}

object Invitation extends Invitation with LongKeyedMetaMapper[Invitation] {
  
}