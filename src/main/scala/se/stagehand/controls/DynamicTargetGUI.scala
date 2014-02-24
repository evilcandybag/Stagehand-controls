package se.stagehand.controls

import se.stagehand.swing.lib.EditorScriptButton
import se.stagehand.swing.lib.EditorScriptNode
import se.stagehand.swing.player.PlayerScriptNode
import se.stagehand.lib.scripting.ScriptComponent
import se.stagehand.lib.scripting.Target
import scala.swing.Dialog
import se.stagehand.swing.gui.BetterDialog
import se.stagehand.swing.gui.BetterDialog.InputDialog
import scala.swing.Button
import scala.swing.Action
import scala.swing.CheckBox
import scala.swing.FlowPanel
import scala.swing.BorderPanel
import scala.swing.BoxPanel
import scala.swing.Orientation

object DynamicTargetGUI extends TargetGroupGUI[DynamicTargets] {
  val peer = classOf[DynamicTargets]
  
  def menuItem(script: ScriptComponent) = {
    checkScript(script)
    new DynamicTargetButton(script.asInstanceOf[peertype])
  }
  def editorNode(script: ScriptComponent) = {
    checkScript(script)
    new DynamicTargetEditorNode(script.asInstanceOf[peertype])
  }
  
  def playerNode(script: ScriptComponent) = {
    checkScript(script)
    new DynamicTargetPlayerNode(script.asInstanceOf[peertype])
  }
  
  class DynamicTargetButton(s:peertype) extends EditorScriptButton(s)
  
  class DynamicTargetEditorNode(s:peertype) extends EditorScriptNode(s)
  
  class DynamicTargetPlayerNode(s:peertype) extends GroupPlayerItem(s) {
    def title = script.displayName
    
    val chck = new CheckBox {
      action = new Action("Random") {
        def apply = {
          if (selected) script.guiHook = random else script.guiHook = pickOne
        }
      }
    }
    
    script.guiHook = pickOne
    
    layout(new FlowPanel {contents += chck}) = BorderPanel.Position.South
    
    val random: Set[Target] => Set[Target] = s => {
      Set(s.toList((Math.random() * s.size).toInt))
    }
    val pickOne: Set[Target] => Set[Target] = s => {
      val t = BetterDialog.inputDialog[Option[Target]](new InputDialog[Option[Target]] {
        private var ot:Option[Target] = None
        def selected = ot
        
        val pan = new BoxPanel(Orientation.Vertical)
        contents = pan
        s.foreach(x => {
          pan.contents += new WrapButton(x)
        })
        
        class WrapButton(val t:Target) extends Button(){
          val croppedName = t.prettyName.substring(0, 15)
          action = new Action(croppedName) {
            def apply {
              ot = Some(t)
              close()
              dispose()
            }
          }
        }
        refresh
      })
      t.toSet
    }
    
  }

}