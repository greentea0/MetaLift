package code
package model

import net.liftweb.mapper._

class Conversation extends LongKeyedMapper[Conversation] with OneToMany[Long, Conversation] with ManyToMany {
  def getSingleton = Conversation
  
  def primaryKeyField = id
  object id extends MappedLongIndex(this)
  object topic extends MappedString(this, 140) {
    override def dbIndexed_? = true
    override def defaultValue = "New Blog Post"
  }
  object startedBy extends MappedLongForeignKey(this, User)
  object startedAt extends MappedDateTime( this )

  object messages extends MappedOneToMany(Message, Message.conversation, OrderBy(Message.id, Ascending))
  object tags extends MappedManyToMany(ConversationTags, ConversationTags.conversation, ConversationTags.tag, Tag)
}

object Conversation extends Conversation with LongKeyedMetaMapper[Conversation] {
  
}

