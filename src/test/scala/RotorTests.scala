import org.scalatest.FlatSpec
import qsim.bloch.core.{Gate, Qubit}
import qsim.bloch.core.operators.Pauli

/**
  * Created by alonso on 25/06/2017.
  */
class RotorTests extends FlatSpec {
  "A Pauli X" should "rotate a bloch sphere pi radians along the x axis" in {
    val state = new Gate(Pauli.X).apply(Qubit.ZERO)

  }
}
