import org.scalatest._
import scala.math._

import qsim.bloch.core.{Gate, PauliGates, Qubit}

/**
  * Created by alonso on 24/06/2017.
  */
class PauliTests extends FlatSpec {

  "A Pauli X" should "invert a qubit" in {
    val initial = new Qubit(1, 0)
    val transformed = new Gate(PauliGates.X).apply(initial)
    assert(initial.q0 == transformed.q1)
    assert(initial.q1 == transformed.q0)
  }

  "A Pauli I" should "leave the qubit intact" in {
    val initial = new Qubit(1, 0)
    val transformed = new Gate(PauliGates.I).apply(initial)
    assert(initial.q0 == transformed.q0)
    assert(initial.q1 == transformed.q1)
  }

  "A Hadamard gate" should "put a qubit in a superposition state" in {
//    val initial = new Qubit(1, 0)
//    val transformed = new Gate(PauliGates.H).apply(initial)
//    assert(initial.q0 == 1/sqrt(2))
//    assert(initial.q1 == 1/sqrt(2))
  }

}
