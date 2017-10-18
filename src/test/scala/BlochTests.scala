import org.scalatest.FlatSpec
import qsim.bloch.BlochSphere
import qsim.bloch.core.Gate
import qsim.bloch.core.Qubit._
import qsim.bloch.core.operators.Pauli


/**
  * Created by alonso on 02/07/2017.
  */
class BlochTests extends FlatSpec {

  "UI" should "work asynchronously" in {
    BlochSphere.start()
    Thread.sleep(800)

    BlochSphere.
      drawSphere().
      draw(ZERO).
      draw(ZERO, new Gate(Pauli.X))
      // TODO: .apply([0..N], gate)
    Thread.sleep(10000)
  }

  "A Pauli X" should "rotate the bloch sphere from |0> to |1> along the Z axis" in {

  }
}
