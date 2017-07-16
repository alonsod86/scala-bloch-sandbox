import javafx.application.Platform

import org.scalatest.FlatSpec
import qsim.bloch.Main


/**
  * Created by alonso on 02/07/2017.
  */
class BlochTests extends FlatSpec {

  "A Pauli X" should "rotate the bloch sphere from |0> to |1> along the Z axis" in {
    Main.start()

    for (i<-0 to 2) {
      Thread.sleep(1000)
      Main.drawBlochSphere()
    }
  }
}
