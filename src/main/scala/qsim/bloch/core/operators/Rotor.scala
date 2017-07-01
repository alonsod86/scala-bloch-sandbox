package qsim.bloch.core.operators

import breeze.linalg.{Matrix, Vector}
import breeze.math.{Complex, _}
import qsim.bloch.core.Qubit

import scala.math.{round, _}

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
  def NONE = new BlochAxis(-1, -1, -1)
}

/**
  * Created by alonso on 25/06/2017.
  */
class Rotor {

  private var u11: Complex = Pauli.I(0, 0)
  private var u12: Complex = Pauli.I(0, 1)
  private var u21: Complex = Pauli.I(1, 0)
  private var u22: Complex = Pauli.I(1, 1)

  var angle: Double = 0.0
  var axis: BlochAxis = Axes.NONE

  def this(angle: Double, axis: BlochAxis) {
    this()
    this.angle = angle
    this.axis = axis

    if (axis == Axes.X_AXIS) {
      this.u11 = format(cosine(angle/2))
      this.u12 = -i * sine(angle/2)
      this.u21 = -i * sine(angle/2)
      this.u22 = format(cosine(angle/2))
    } else if (axis == Axes.Y_AXIS) {
      this.u11 = format(cosine(angle/2))
      this.u12 = -format(sine(angle/2))
      this.u21 = format(sine(angle/2))
      this.u22 = format(cosine(angle/2))
    } else if (axis == Axes.Z_AXIS) {
      this.u11 = exponential(Complex(0, -toDegrees(angle/2)))
      this.u12 = Complex.zero
      this.u21 = Complex.zero
      this.u22 = exponential(Complex(0, toDegrees(angle/2)))
    }
  }

  private def cosine = (a: Double) => {round(cos(toDegrees(a)))}
  private def sine = (a: Double) => {round(sin(toDegrees(a)))}
  private def format = (a: Double) => {Complex(a, 0)}

  /* exponentiation (e to a+bi) */
  private def exponential = (z: Complex) => {
    if (z.imag == 0.0) {
      Complex(StrictMath.exp(z.real), 1)
    } else {
      val ex = Complex(StrictMath.exp(z.real), 0) * Complex(StrictMath.cos(z.imag), StrictMath.sin(z.imag))
      Complex(round(ex.real), round(ex.imag))
    }
  }

  def apply(qubit: Qubit): Qubit = {
    val operator: Matrix[Complex] = Matrix((u11, u12), (u21, u22))
    val stateVector: Vector[Complex] = Vector(qubit.q0, qubit.q1)
    val transformedStateVector: Vector[Complex] = operator * stateVector
    new Qubit(transformedStateVector(0), transformedStateVector(1))
  }

}
