package code {
package model {

import net.liftweb.mapper._

	class Tag extends LongKeyedMapper[Tag] with ManyToMany {
	  def getSingleton = Tag
	
	  def primaryKeyField = id
	  object id extends MappedLongIndex(this)
	  object tag extends MappedString(this, 10)
	  object conversations extends MappedManyToMany(ConversationTags, ConversationTags.tag, ConversationTags.conversation, Conversation)
	}

	object Tag extends Tag with LongKeyedMetaMapper[Tag]

	object ConversationTags extends ConversationTags with LongKeyedMetaMapper[ConversationTags]

	class ConversationTags extends LongKeyedMapper[ConversationTags] with IdPK {
	  def getSingleton = ConversationTags
	  object conversation extends MappedLongForeignKey(this, Conversation)
	  object tag extends MappedLongForeignKey(this, Tag)
	}

}
}