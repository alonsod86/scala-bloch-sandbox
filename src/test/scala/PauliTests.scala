import org.scalatest._
import scala.math._

import qsim.bloch.core.{Gate, Pauli, Qubit}
import breeze.math.Complex._
/**
  * Created by alonso on 24/06/2017.
  */
class PauliTests extends FlatSpec {

  "A Hadamard gate" should "put a qubit in a superposition state" in {
    val initial = new Qubit(one, zero)
    val transformed = new Gate(Pauli.H).apply(initial)
    assert(transformed.q0 == one/sqrt(2))
    assert(transformed.q1 == one/sqrt(2))
  }

  "A Pauli X" should "invert a qubit" in {
    val initial = new Qubit(one, zero)
    val transformed = new Gate(Pauli.X).apply(initial)
    assert(initial.q0 == transformed.q1)
    assert(initial.q1 == transformed.q0)
  }

  "A Pauli I" should "leave the qubit intact" in {
    val initial = new Qubit(one, zero)
    val transformed = new Gate(Pauli.I).apply(initial)
    assert(initial.q0 == transformed.q0)
    assert(initial.q1 == transformed.q1)
  }

  "A Pauli Z" should "invert a superposition qubit" in {
    val initial = new Qubit(one, zero)
    val transformed = initial
      .apply(Pauli.H)
      .apply(Pauli.Z)
      .apply(Pauli.H)
    assert(ceil(transformed.q0.real) == 0)
    assert(ceil(transformed.q1.real) == 1)
    assert(transformed.q0.imag == 0)
    assert(transformed.q1.imag == 0)
  }

}
