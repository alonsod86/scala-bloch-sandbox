package qsim.bloch.core.operators

import breeze.linalg.{Matrix, Vector}
import breeze.math.Complex
import qsim.bloch.core.Qubit

import scala.math.{round, _}
import breeze.math._

class BlochAxis (var x: Double, var y:Double, var z:Double) {
  def canEqual(other: Any): Boolean = other.isInstanceOf[BlochAxis]

  override def equals(other: Any): Boolean = other match {
    case that: BlochAxis =>
      (that canEqual this) &&
        x == that.x &&
        y == that.y &&
        z == that.z
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(x, y, z)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

object Axes {
  def X_AXIS = new BlochAxis(1, 0, 0)
  def Y_AXIS = new BlochAxis(0, 1, 0)
  def Z_AXIS = new BlochAxis(0, 0, 1)
}

/**
  * Created by alonso on 25/06/2017.
  */
class Rotor {

  var u11: Complex = null
  var u12: Complex = null
  var u21: Complex = null
  var u22: Complex = null

  def this(angle: Double, axis: BlochAxis) {
    this()
    if (axis == Axes.X_AXIS) {
      this.u11 = Complex(round(cos(toDegrees(angle/2))), 0)
      this.u12 = -i * round(sin(toDegrees(angle/2)))
      this.u21 = -i * round(sin(toDegrees(angle/2)))
      this.u22 = Complex(round(cos(toDegrees(angle/2))), 0)
    }
  }

  def apply(qubit: Qubit): Qubit = {
    val operator: Matrix[Complex] = Matrix((u11, u12), (u21, u22))
    val stateVector: Vector[Complex] = Vector(qubit.q0, qubit.q1)
    val transformedStateVector: Vector[Complex] = operator * stateVector
    new Qubit(transformedStateVector(0), transformedStateVector(1))
  }
}
