package qsim.bloch.core

import qsim.bloch.ui.View

/**
  * Created by alonso on 18/06/2017.
  */
object Register {
  var gates: List[Gate] = List()
  var stack: List[Qubit] = List()

  // current instruction pointer
  var ipointer = 0

  // status of the bloch sphere relative to ipointer
  var bloch: View = null

  def addGate(gate: Gate, index: Int): Unit = {

  }

}
