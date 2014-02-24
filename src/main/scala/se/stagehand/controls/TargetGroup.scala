package se.stagehand.controls

import se.stagehand.lib.scripting.ScriptComponent
import se.stagehand.lib.scripting.ID
import se.stagehand.lib.scripting.Target

class TargetGroup(id:Int) extends ScriptComponent(id) {
  def this() = this(ID.unique)

  def componentName = "Target Group"
  val description = "A group of targets bundled as one"
  
  var _targets = Set[Target]()  
  def addTarget(t:Target) = _targets += t
  def removeTarget(t:Target) = _targets -= t
  def targets = _targets
  
  lazy val myTarget = new Target(displayName, Array(), "Group targetting ftw"){
    import Target.Protocol._
    def callback(args:Arguments) {
      targets.foreach(_.callback(args))
    }
    def run(args:Arguments) {
      targets.foreach(_.run(args))
    }
    
    override def capabilities = targets.map(_.capabilities).flatten.toArray
  }
  
  Target.addSource(() => Set(myTarget))
  
  //This guy should not be used
  def executeInstructions(params:Any*)   {
  }
  
  trait GroupTarget extends Target
    
}