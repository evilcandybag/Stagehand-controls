package se.stagehand.controls

import se.stagehand.plugins._
import se.stagehand.lib.scripting._

class ControlsPlugin extends ScriptPlugin {

  val name = "ControlsPlugin"
    
  val guis = List(HotkeyGUI)
  def scriptcomponents: Array[ScriptComponent] = Array(new Hotkey)
  
}