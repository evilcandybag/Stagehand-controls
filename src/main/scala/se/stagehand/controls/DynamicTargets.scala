package se.stagehand.controls

import se.stagehand.lib.scripting.ScriptComponent
import se.stagehand.lib.scripting.ID
import se.stagehand.lib.scripting.Target
import se.stagehand.lib.Log

class DynamicTargets(id:Int) extends TargetGroup(id) {
  def this() = this(ID.unique)
  private val log = Log.getLog(this.getClass())

  override def componentName = "Dynamic Targets"
  override val description = "Store dynamic targets here"
  
  var guiHook: (Set[Target]) => Set[Target] = x => Set()
  
  override lazy val myTarget = new Target(displayName, Array(), "Dynamic targetting ftw"){
    import Target.Protocol._
    def callback(args:Arguments) {
      targets.foreach(_.callback(args))
    }
    def run(args:Arguments) {
      if (guiHook == null) guiHook = x => Set()
      val tars = guiHook(targets)
      log.debug("" + tars)
      tars.foreach(_.run(args))
    }
    
    override def capabilities = targets.map(_.capabilities).flatten.toArray

  }
    
}