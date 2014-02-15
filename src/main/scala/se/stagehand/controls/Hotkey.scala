package se.stagehand.controls

import se.stagehand.lib.scripting._
import scala.xml._
import scala.swing._
import scala.swing.event.Key

class Hotkey(id:Int) extends AbstractControl(id) {
  def this() = this(ID.unique)
  
  override def componentName = "Hotkey"
  val description = "A script that produces an output signal when a key is pressed."
    
  var key = Key.Undefined
  
  def executeInstructions(params: Any*){
    signal(Start[Key.Value](key))
  }
  
  override def readInstructions(in: Node) {
    super.readInstructions(in)
    
    key = Key.apply((in \ "key").text.toInt)
    
  }
  
  override def generateInstructions = {
    implicit var xml = super.generateInstructions
    xml = addChild(<key>{key.id}</key>)
    xml = addChild(outputsXML)
    
    xml
  }
}