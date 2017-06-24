package qsim.bloch.core

import breeze.linalg.{DenseMatrix, DenseVector}


/**
  * Created by alonso on 18/06/2017.
  */
class Gate(var matrix: DenseMatrix[Int]) {

  def apply(qubit: Qubit): Qubit = {
    val qvector = DenseVector(qubit.q0, qubit.q1)
    var output: DenseVector[Int] = matrix * qvector
    new Qubit(output.valueAt(0), output.valueAt(1))
  }
}
