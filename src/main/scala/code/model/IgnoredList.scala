package code
package model

import net.liftweb.mapper.LongKeyedMetaMapper
import net.liftweb.mapper.LongKeyedMapper
import net.liftweb.mapper._


class IgnoredList  extends LongKeyedMapper[IgnoredList] {
   def getSingleton = IgnoredList
	
  def primaryKeyField = id
  object id extends MappedLongIndex(this)
  object userID extends MappedLongForeignKey(this, User)
  object joined extends MappedDateTime( this )
}

object IgnoredList extends IgnoredList with LongKeyedMetaMapper[IgnoredList] {
  
}