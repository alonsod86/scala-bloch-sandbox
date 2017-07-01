package qsim.bloch.core

import breeze.linalg.{Matrix, Vector}
import breeze.math.Complex
import breeze.numerics.constants._
import qsim.bloch.core.operators.{Axes, Pauli, Rotor}

/**
  * Created by alonso on 18/06/2017.
  */
class Gate(private var matrix: Matrix[Complex], private var rotor: Rotor) {

  def this(matrix: Matrix[Complex]) {
    this(matrix, null)
    rotor = matrix match {
      case Pauli.X => new Rotor(Pi, Axes.X_AXIS)
      case Pauli.Y => new Rotor(Pi, Axes.Y_AXIS)
      case Pauli.Z => new Rotor(Pi, Axes.Z_AXIS)
      // TODO: implement n-axis rotation for non-basis
      case _ => new Rotor(0, Axes.NONE)
    }

  }

  def apply(qubit: Qubit): Qubit = {
    val state = Vector(qubit.q0, qubit.q1)
    val output: Vector[Complex] = matrix * state
    new Qubit(output(0), output(1))
  }

  def getRotor() = rotor
  def getTransformation() = matrix
  def angle() = getRotor().angle
  def axis() = getRotor().axis
}
