package code {
package model {

import net.liftweb.mapper._

	object Friendship extends Friendship with LongKeyedMetaMapper[Friendship]

	class Friendship extends LongKeyedMapper[Friendship] with IdPK {
	  def getSingleton = Friendship
	  
	  object user1  extends MappedLongForeignKey(this, User)
	  object user2  extends MappedLongForeignKey(this, User)
	  object friended extends MappedDateTime( this )
	}

}
}