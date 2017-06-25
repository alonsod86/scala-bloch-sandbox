package qsim.bloch.core.operators

import breeze.linalg.Matrix
import breeze.math.Complex._

import scala.math._

/**
  * Created by alonso on 15/06/2017.
  */
object Pauli {
  val I = Matrix((one, zero), (zero, one))
  val X = Matrix((zero, one), (one, zero))
  val Y = Matrix((zero, -i), (i, zero))
  val Z = Matrix((one, zero), (zero, -one))
  val H = Matrix((one/sqrt(2), one/sqrt(2)), (one/sqrt(2), -one/sqrt(2)))
}
