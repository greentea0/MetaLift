package code
package model

import net.liftweb.mapper._


class TrendWord extends LongKeyedMapper[TrendWord] {
  def getSingleton = TrendWord
	
  
  def primaryKeyField = id
  object id extends MappedLongIndex(this)
  object word extends MappedString( this, 140 )
  object introduced extends MappedDateTime( this )
  
}


object TrendWord extends TrendWord with LongKeyedMetaMapper[TrendWord] {
  
}