package code
package snippet

import net.liftweb._
import code.model._
import net.liftweb.widgets.menu._
import _root_.scala.xml.NodeSeq

object CustomMenu {
	def render(xhtml: NodeSeq): NodeSeq = { 
	    MenuWidget(MenuStyle.HORIZONTAL) 
	} 
}