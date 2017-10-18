package qsim.bloch

import java.util.concurrent.atomic.AtomicBoolean
import javafx.application.Platform
import javafx.embed.swing.JFXPanel

import breeze.numerics.toDegrees
import qsim.bloch.core.operators.Axes
import qsim.bloch.core.{Gate, Qubit}
import qsim.bloch.ui.ViewController

import scala.collection.mutable.ArrayBuffer
import scalafx.application.JFXApp
import scalafx.scene._
import scalafx.scene.paint.Color

class UIThread extends Runnable {
  def run {
    BlochSphere.main(null)
  }
}

/**
  * Created by alonso on 12/06/2017.
  */
object BlochSphere extends JFXApp { app =>
  System.setProperty("prism.dirtyopts", "false")
  private var stateVectors: ArrayBuffer[Group] = new ArrayBuffer[Group]()
  private val parentScene : Group = new Group
  private val camera : PerspectiveCamera = new PerspectiveCamera(true)

  private final val sceneSize = 10
  private final val axesScaleFactor = 1.3

  private final val updatingUI: AtomicBoolean = new AtomicBoolean(true)

  def start(): Unit = {
    new JFXPanel // avoid Toolkit initialization exception
    new Thread(new UIThread).start
  }

  this.stage = new JFXApp.PrimaryStage {
    scene = new Scene(parentScene, 1024, 768, true, SceneAntialiasing.Balanced) {
      fill = Color.Black
      title = "Bloch Sphere"
      camera = app.camera
    }

    ViewController.initScene(parentScene, sceneSize, axesScaleFactor)
    ViewController.initCamera(camera)
    ViewController.initMouseHandler(scene())

    updatingUI.getAndSet(false)
  }

  /** Draws the bloch sphere in state |0> */
  def drawSphere() = {
    innerDraw(() => {
      ViewController.drawBlochSphere
      ViewController.drawAxes
    })

    this
  }

  def draw(qubit: Qubit) = {
    // |psi> =cos(theta/2) |0> + e^(i*phi)sin(theta/2)|1>

    if (!qubit.equals(Qubit.ZERO) && !qubit.equals(Qubit.ONE))
    throw new RuntimeException("Only ZERO and ONE states are allowed")

    innerDraw(() => {
      val groundState: Group = ViewController.drawStateVector(Color.Red, Axes.Z_AXIS)

      stateVectors+=(groundState)
      if (qubit.equals(Qubit.ONE)) {
        ViewController.rotateStateVector(groundState, 180)
      }
    })

    this
  }

  def draw(qubit: Qubit, gate: Gate) = {
    // TODO: check that rotor is not (0, NONE)
    val radians = gate.angle()
    val axis = gate.axis()

    // TODO: FETCH VECTOR
    innerDraw(() => {
      val groundState: Group = ViewController.drawStateVector(Color.Red, Axes.Z_AXIS)
      ViewController.rotateStateVector(groundState, toDegrees(radians))
    })

    this
  }

  private def innerDraw(callback: Runnable): Unit = {
    if (updatingUI==null || updatingUI.compareAndSet(false, true)) {
      println("FREE!!")
      Platform.runLater(()=> {
        callback.run()
        updatingUI.getAndSet(false)
        println("Unreserving")
      })
    } else {
      println("Not ready yet...")
      Thread.sleep(1)
      innerDraw(callback)
    }
  }

}
