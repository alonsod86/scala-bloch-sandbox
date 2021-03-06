import breeze.math.{Complex, _}
import breeze.numerics.sqrt
import org.scalatest.FlatSpec
import qsim.bloch.core.{Gates, Qubit}
import qsim.bloch.core.Qubit._
import qsim.bloch.core.operators.{Axes, Pauli, Rotor}

/**
  * Created by alonso on 25/06/2017.
  */
class RotorTests extends FlatSpec {

  "Rotating X Pi radians" should "invert the qubit along the Z axis" in {
    var transformed: Qubit = new Rotor(Math.PI, Axes.X_AXIS)(Qubit.ZERO).asInstanceOf[Qubit]
    assert(transformed.q0.equals(Complex.zero))
    assert(transformed.q1.equals(Complex(0, -1)))
    transformed = new Rotor(Math.PI, Axes.X_AXIS)(Qubit.ONE).asInstanceOf[Qubit]
    assert(transformed.q0.equals(Complex(0, -1)))
    assert(transformed.q1.equals(Complex.zero))
  }

  "Rotating Y Pi radians" should "invert the qubit along the Z axis" in {
    var transformed: Qubit = new Rotor(Math.PI, Axes.Y_AXIS)(Qubit.ZERO).asInstanceOf[Qubit]
    assert(transformed.q0.equals(Complex.zero))
    assert(transformed.q1.equals(Complex.one))
    transformed = new Rotor(Math.PI, Axes.Y_AXIS)(Qubit.ONE).asInstanceOf[Qubit]
    assert(transformed.q0.equals(Complex(-1, 0)))
    assert(transformed.q1.equals(Complex.zero))
  }

  "Rotating Z pi radians" should "leave intact the qubit along the Z axis" in {
    var transformed: Qubit = new Rotor(Math.PI, Axes.Z_AXIS)(Qubit.ZERO).asInstanceOf[Qubit]
    assert(transformed.q0.equals(Complex(0, -1)))
    assert(transformed.q1.equals(Complex.zero))
    transformed = new Rotor(Math.PI, Axes.Z_AXIS)(Qubit.ONE).asInstanceOf[Qubit]
    assert(transformed.q0.equals(Complex.zero))
    assert(transformed.q1.equals(i))
  }

  "An NONE rotor" should "apply an I transformation along any axis" in {
    assert(new Rotor()(Qubit.ZERO).equals(Qubit.ZERO))
    assert(new Rotor()(Qubit.ONE).equals(Qubit.ONE))
    assert(
      new Rotor()(new Qubit(1/sqrt(2), 1/sqrt(2))).equals(new Qubit(1/sqrt(2), 1/sqrt(2)))
    )
  }

  "A Pauli X" should "act like a rotation of Pi radians along the X axis" in {
    var throughGate = ZERO(Pauli.X)
    // simplify (0, -i) -> -i(0,1) dividing by -i
    var throughRotor = ZERO(new Rotor(Math.PI, Axes.X_AXIS)) / -i
    assert(throughGate.equals(throughRotor))
    // excited state
    throughGate = ONE(Pauli.X)
    throughRotor = ONE(new Rotor(Math.PI, Axes.X_AXIS)) / -i
    assert(throughGate.equals(throughRotor))
  }

  "A Pauli Y" should "act like a rotation of Pi radians along the Y axis" in {
    // simplify (0, -i)(i, 0) -> i(0, -1)(1, 0) dividing by i
    var throughGate = ZERO(Pauli.Y) / i
    var throughRotor = ZERO(new Rotor(Math.PI, Axes.Y_AXIS))
    assert(throughGate.equals(throughRotor))

    throughGate = ONE(Pauli.Y) / i
    throughRotor = ONE(new Rotor(Math.PI, Axes.Y_AXIS))
    assert(throughGate.equals(throughRotor))
  }

  "A Pauli Z" should "act like a rotation of Pi radians along the Z axis" in {
    // simplify (0, -i)(i, 0) -> i(0, -1)(1, 0) dividing by i
    var throughGate = ZERO(Pauli.Z) / -1
    var throughRotor = ZERO(new Rotor(Math.PI, Axes.Z_AXIS)) / i
    assert(throughGate.equals(throughRotor))
    throughGate = ONE(Pauli.Z) / -1
    throughRotor = ONE(new Rotor(Math.PI, Axes.Z_AXIS)) / i
    assert(throughGate.equals(throughRotor))
  }
}
