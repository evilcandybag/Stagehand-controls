package se.stagehand.controls

import se.stagehand.lib.scripting._
import scala.xml._
import scala.swing._
import scala.swing.event.Key

class Hotkey extends AbstractControl {
  override def componentName = "Hotkey"
    
  var key = Key.Undefined
  
  def executeInstructions {
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