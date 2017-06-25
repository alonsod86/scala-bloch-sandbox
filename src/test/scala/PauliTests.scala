import org.scalatest._

import scala.math._
import qsim.bloch.core.{Gate, Qubit}
import breeze.math.Complex._
import qsim.bloch.core.operators.Pauli
/**
  * Created by alonso on 24/06/2017.
  */
class PauliTests extends FlatSpec {

  "A Hadamard gate" should "put a qubit in a superposition state and back" in {
    val initial = Qubit.ZERO
    val transformed = new Gate(Pauli.H).apply(initial)
    assert(transformed.q0 == one/sqrt(2))
    assert(transformed.q1 == one/sqrt(2))

    val restored = transformed.apply(Pauli.H)
    assert(ceil(restored.q0.real) == 1)
    assert(ceil(restored.q1.real) == 0)
  }

  "A Pauli I" should "leave the qubit intact" in {
    val initial = new Qubit(1, 0)
    val transformed = new Gate(Pauli.I).apply(initial)
    assert(initial.q0 == transformed.q0)
    assert(initial.q1 == transformed.q1)
  }

  "A Pauli X" should "invert a qubit" in {
    val initial = new Qubit(1, 0)
    val transformed = new Gate(Pauli.X).apply(initial)
    assert(initial.q0 == transformed.q1)
    assert(initial.q1 == transformed.q0)
  }

  "A Pauli Y" should "invert a qubit on the Z axis" in {
    val initial = new Qubit(1, 0)
    val transformed = new Gate(Pauli.Y).apply(initial)
    assert(transformed.q0.real == 0)
    assert(transformed.q1.imag == 1)
  }

  "A Pauli Z" should "invert a superposition qubit" in {
    val initial = new Qubit(1, 0)
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
