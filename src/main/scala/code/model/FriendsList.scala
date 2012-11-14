package code
package model

import net.liftweb.mapper.LongKeyedMetaMapper
import net.liftweb.mapper.LongKeyedMapper
import net.liftweb.mapper._


class FriendsList  extends LongKeyedMapper[FriendsList] {
   def getSingleton = FriendsList
	
  def primaryKeyField = id
  object id extends MappedLongIndex(this)
  object requesterID extends MappedLongForeignKey(this, User)
  object friendID extends MappedLongForeignKey(this, User)
  object joined extends MappedDateTime( this )
}

object FriendsList extends FriendsList with LongKeyedMetaMapper[FriendsList] {
  
}