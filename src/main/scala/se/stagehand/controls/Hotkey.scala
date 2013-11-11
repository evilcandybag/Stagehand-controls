package se.stagehand.controls

import se.stagehand.lib.scripting._
import scala.xml.Elem
import scala.swing._
import scala.swing.event.Key

class Hotkey extends AbstractControl {
  override def componentName = "Hotkey"
    
  var key = Key.Undefined
  
  def executeInstructions {
    signal(Start[Key.Value](key))
  }
  
  def readInstructions(in: Elem) {}
  
  override def generateInstructions = {
    implicit var xml = super.generateInstructions
    xml = addChild(<key name={key.toString()}/>)
    xml = addChild(outputsXML)
    
    xml
  }
    
  
  

}