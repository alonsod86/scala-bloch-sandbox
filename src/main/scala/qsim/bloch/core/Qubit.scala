package qsim.bloch.core

import breeze.linalg.Matrix
import breeze.math.Complex
import qsim.bloch.core.operators.Rotor

object Qubit {
  def ZERO = new Qubit(1, 0)
  def ONE = new Qubit(0, 1)
}

/**
  * Created by alonso on 18/06/2017.
  */
class Qubit(var q0: Complex, var q1: Complex)  {

  // TODO: Define theta and phi

  def this(q0: Int, q1: Int) = {
    this(Complex(q0, 0), Complex(q1, 0))
  }

  def this(q0: Double, q1: Double) = {
    this(Complex(q0, 0), Complex(q1, 0))
  }

  /** Apply a transformation in it's matrix form over this qubit */
  def apply(operator: Matrix[Complex]) = new Gate(operator)(this).asInstanceOf[Qubit]

  /** Apply a transformation in it's gate form over this qubit */
  def apply(gate: Gate): Qubit = gate(this)

  /** Apply a transformation using a rotor */
  def apply(rotor: Rotor) = rotor(this)

  def / (d: Double) = {
    new Qubit(this.q0 / d, this.q1 / d)
  }

  def / (c: Complex) = {
    new Qubit(this.q0 / c, this.q1 / c)
  }

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
