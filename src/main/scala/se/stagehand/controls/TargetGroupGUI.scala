package se.stagehand.controls

import scala.swing.BorderPanel
import se.stagehand.swing.lib.ScriptGUI
import se.stagehand.swing.player.PlayerScriptNode
import scala.swing.FlowPanel
import scala.swing.CheckBox
import scala.swing.Label
import se.stagehand.swing.gui.PopupMenu
import scala.swing.MenuItem
import scala.swing.Action
import scala.swing.event.MouseClicked
import scala.swing.BoxPanel
import se.stagehand.lib.scripting.Target
import javax.swing.SwingUtilities
import java.awt.MouseInfo
import scala.swing.Orientation
import scala.swing.Button
import se.stagehand.swing.assets.ImageAssets
import java.awt.Dimension
import se.stagehand.swing.player.NetworkedTargetPicker
import se.stagehand.swing.player.Sidebarred
import se.stagehand.lib.scripting.ScriptComponent
import se.stagehand.swing.lib.EditorScriptButton
import se.stagehand.swing.lib.EditorScriptNode
import se.stagehand.lib.Log

abstract class TargetGroupGUI[T <: TargetGroup] extends ScriptGUI {
  type peertype = T
  
  abstract class GroupPlayerItem(s: peertype) extends PlayerScriptNode[peertype](s) with Sidebarred[peertype] {
    protected val log = Log.getLog(this.getClass())
    val list = new BoxPanel(Orientation.Vertical) {
      
    }
    val butt = new Button("") {
      preferredSize = new Dimension(15,15)
      
      action = new Action("") {
        icon = ImageAssets.TARGET_ICON
        def apply = {
          NetworkedTargetPicker.pickTargets( s => {
            s
          }, t => {
            if (t != script.myTarget) {
              script.addTarget(t)
              list.contents += new ListWrapper(t)
              log.debug("Added target: " + t)
            }
          }, script.targets, t => {
            script.removeTarget(t)
            val tar = list.contents.find(_ match {
              case li:ListWrapper => li.target == t
              case _ => false
            })
            tar match {
              case Some(l:ListWrapper) => list.contents -= l
              case _ => {}
            }
          })
          refresh
        }
      }
    }
    
    val panel = new BorderPanel {
      layout(list) = BorderPanel.Position.Center
      layout(new FlowPanel{contents += butt}) = BorderPanel.Position.East
    }
    
    layout(panel) = BorderPanel.Position.Center
    
    class ListWrapper(val target:Target) extends Label {
      var me = this
      text = target.prettyName
      
      reactions += {
      	case e: MouseClicked if SwingUtilities.isRightMouseButton(e.peer)  => {
      	  var cursor = MouseInfo.getPointerInfo.getLocation
          SwingUtilities.convertPointFromScreen(cursor, this.peer)
          contextMenu.show(this, cursor.x, cursor.y)
        }
      }
  
      private val contextMenu = new PopupMenu {
    	 contents += new MenuItem(new Action("Delete") {
    		 def apply {
    			 list.contents -= me
    			 script.removeTarget(target)
    		 }
    	 })
      }
      
    }
  }
  
  
  
}

object TargetGroupGUI extends TargetGroupGUI[TargetGroup] {
  val peer = classOf[TargetGroup]
  
  def menuItem(script: ScriptComponent) = {
    checkScript(script)
    new TargetGroupButton(script.asInstanceOf[peertype])
  }
  def editorNode(script: ScriptComponent) = {
    checkScript(script)
    new TargetGroupEditorNode(script.asInstanceOf[peertype])
  }
  
  def playerNode(script: ScriptComponent) = {
    checkScript(script)
    new TargetGroupPlayerNode(script.asInstanceOf[peertype])
  }
  
  class TargetGroupButton(s: peertype) extends EditorScriptButton(s)
  
  class TargetGroupEditorNode(s: peertype) extends EditorScriptNode(s)
  
  class TargetGroupPlayerNode(s: peertype) extends GroupPlayerItem(s) {
    def title = script.displayName
  }
  
}