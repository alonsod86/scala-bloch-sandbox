import breeze.math.{Complex, _}
import org.scalatest.FlatSpec
import qsim.bloch.core.Qubit
import qsim.bloch.core.operators.{Axes, Rotor}

/**
  * Created by alonso on 25/06/2017.
  */
class RotorTests extends FlatSpec {

  "A Pauli X" should "invert the qubit along the Z axis" in {
    var transformed: Qubit = new Rotor(Math.PI, Axes.X_AXIS)(Qubit.ZERO).asInstanceOf[Qubit]
    assert(transformed.q0.equals(Complex.zero))
    assert(transformed.q1.equals(Complex(0, -1)))
    transformed = new Rotor(Math.PI, Axes.X_AXIS)(Qubit.ONE).asInstanceOf[Qubit]
    assert(transformed.q0.equals(Complex(0, -1)))
    assert(transformed.q1.equals(Complex.zero))
  }

  "A Pauli Y" should "invert the qubit along the Z axis" in {
    var transformed: Qubit = new Rotor(Math.PI, Axes.Y_AXIS)(Qubit.ZERO).asInstanceOf[Qubit]
    assert(transformed.q0.equals(Complex.zero))
    assert(transformed.q1.equals(Complex.one))
    transformed = new Rotor(Math.PI, Axes.Y_AXIS)(Qubit.ONE).asInstanceOf[Qubit]
    assert(transformed.q0.equals(Complex(-1, 0)))
    assert(transformed.q1.equals(Complex.zero))
  }

  "A Pauli Z" should "leave intact the qubit along the Z axis" in {
    var transformed: Qubit = new Rotor(Math.PI, Axes.Z_AXIS)(Qubit.ZERO).asInstanceOf[Qubit]
    assert(transformed.q0.equals(Complex(0, -1)))
    assert(transformed.q1.equals(Complex.zero))
    transformed = new Rotor(Math.PI, Axes.Z_AXIS)(Qubit.ONE).asInstanceOf[Qubit]
    assert(transformed.q0.equals(Complex.zero))
    assert(transformed.q1.equals(i))
  }
}
