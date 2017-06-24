package qsim.bloch.core

import breeze.linalg.{Matrix, Vector}
import breeze.math.Complex


/**
  * Created by alonso on 18/06/2017.
  */
class Gate(var matrix: Matrix[Complex] ) {

  def apply(qubit: Qubit): Qubit = {
    val qvector = Vector(qubit.q0, qubit.q1)
    val output: Vector[Complex] = matrix * qvector
    new Qubit(output(0), output(1))
  }
}
