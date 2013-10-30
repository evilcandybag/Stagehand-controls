package se.stagehand.controls

import se.stagehand.gui.components._
import se.stagehand.lib.scripting.ScriptComponent

object HotkeyGUI extends ScriptGUI {
  val peer = classOf[Hotkey]
  register
  
  def menuItem(script: ScriptComponent) = {
    new HotkeyButton(script)
  }
  def editorNode(script: ScriptComponent) = {
    new HotkeyNode(script)
  }
}

class HotkeyButton(peer: ScriptComponent) extends AbstractScriptButton(peer) {
  type peertype = Hotkey
}

class HotkeyNode(peer: ScriptComponent) extends AbstractScriptNode(peer) {
  type peertype = Hotkey
}