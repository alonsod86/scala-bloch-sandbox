package qsim.bloch.core

import breeze.linalg.{Matrix, Vector}
import breeze.math.Complex
import breeze.numerics.constants._
import qsim.bloch.core.operators.{Axes, Pauli, Rotor}

/**
  * Created by alonso on 18/06/2017.
  */
class Gate(var matrix: Matrix[Complex], var rotor: Rotor) {

  def this(matrix: Matrix[Complex]) {
    this(matrix, null)
    rotor = matrix match {
      case Pauli.X => new Rotor(Pi, Axes.X_AXIS)
      case Pauli.Y => new Rotor(Pi, Axes.Y_AXIS)
      case Pauli.Z => new Rotor(Pi, Axes.Z_AXIS)
    }

  }

  def apply(qubit: Qubit): Qubit = {
    rotor.apply(qubit)
    val qvector = Vector(qubit.q0, qubit.q1)
    val output: Vector[Complex] = matrix * qvector
    new Qubit(output(0), output(1))
  }


}
