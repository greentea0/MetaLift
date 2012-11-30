package code
package model

import net.liftweb.mapper._


class Trend extends LongKeyedMapper[Trend] {
  def getSingleton = Trend
	
  def primaryKeyField = id
  object id extends MappedLongIndex(this)
  object user extends MappedLongForeignKey( this , User )
  object conversation extends MappedLongForeignKey( this, Conversation )
  object word extends MappedLongForeignKey( this, TrendWord )
  object occurrence extends MappedInt( this )
  object reportType extends MappedInt(this)
  
}

object Trend extends Trend with LongKeyedMetaMapper[Trend] {
  
}