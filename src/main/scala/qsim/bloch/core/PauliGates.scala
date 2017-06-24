package qsim.bloch.core

import breeze.linalg.DenseMatrix
import breeze.math._
import scala.math._

/**
  * Created by alonso on 15/06/2017.
  */
object PauliGates {

  // Pauli Gates
  val I = DenseMatrix((1, 0), (0, 1))
  val X = DenseMatrix((0, 1), (1, 0))
  val Y = DenseMatrix((Complex(0, 0), Complex(0, -1)), (Complex(0, 1), Complex(0, 0)))
  val Z = DenseMatrix((1, 0), (0, -1))
  val H = 1/sqrt(2) * DenseMatrix((1, 1), (1, -1))
}
