package se.stagehand.controls

import se.stagehand.lib.scripting._
import scala.collection.immutable.ListSet
import scala.xml.Elem

/**
 * Abstract class for scriptcomponents that handle controls such as keyboard 
 * input. 
 */
abstract class AbstractControl(id:Int) extends ScriptComponent(id) with Output {
  def this() = this(ID.unique)
  
  def signal(msg: ScriptMessage) {
    receivers.foreach( x => 
      x ! msg
    )
  }
  
}