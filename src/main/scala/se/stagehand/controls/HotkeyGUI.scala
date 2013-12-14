package se.stagehand.controls

import se.stagehand.lib.scripting.ScriptComponent
import scala.swing._
import scala.swing.event.Key
import scala.swing.event.KeyTyped
import scala.swing.event.KeyPressed
import se.stagehand.swing.lib._
import scala.swing.event.KeyPressed
import se.stagehand.swing.player.PlayerScriptNode

object HotkeyGUI extends ScriptGUI {
  val peer = classOf[Hotkey]
  register
  
  def menuItem(script: ScriptComponent) = {
    if (script.getClass == peer)
      new HotkeyButton(script.asInstanceOf[Hotkey])
    else
      throw new IllegalArgumentException("Script needs to be a Hotkey!")
  }
  def editorNode(script: ScriptComponent) = {
    if (script.getClass == peer)
    	new HotkeyNode(script.asInstanceOf[Hotkey])
    else 
      throw new IllegalArgumentException("Script needs to be a Hotkey!")
  }
  
  def playerNode(script: ScriptComponent) = {
    if (script.getClass == peer)
      new HotkeyPlayerNode(script.asInstanceOf[Hotkey])
    else 
      throw new IllegalArgumentException("script needs to be a hotkey")
  }
}

class HotkeyButton(script: Hotkey) extends EditorScriptButton(script) {
}

class HotkeyPlayerNode(script: Hotkey) extends PlayerScriptNode[Hotkey](script) {
  def title = script.displayName
}

class HotkeyNode(script: Hotkey) extends EditorScriptNode[Hotkey](script) with OutputGUI[Hotkey] {
  val me = this
  
  val butt:Button = new Button("") {
    
    action = new Action("") {
      def apply {
        script.key = Dialog.showInput[Key.Value](me, "Choose key", "Choose key", Dialog.Message.Question, Swing.EmptyIcon, Key.values.toSeq, script.key) match {
          case Some(k:Key.Value) => {
            k
          }
          case None => script.key
        }
        updateText
      }
    }
    
    def updateText {
      text = script.key.toString
      me.peer.setSize(me.preferredSize)
    }
  }
  pan.layout(butt) = BorderPanel.Position.Center
}