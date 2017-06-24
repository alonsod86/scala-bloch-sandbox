package qsim.bloch.core

import breeze.linalg.Matrix
import breeze.math.Complex

/**
  * Created by alonso on 18/06/2017.
  */
class Qubit(var q0: Complex, var q1: Complex)  {

  def apply(operator: Matrix[Complex]) = new Gate(operator).apply(this)
  def apply(gate: Gate): Qubit = gate.apply(this)

  override def toString = s"Q($q0, $q1)"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Qubit]

  override def equals(other: Any): Boolean = other match {
    case that: Qubit =>
      (that canEqual this) &&
        q0 == that.q0 &&
        q1 == that.q1
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(q0, q1)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
