package qsim.bloch

import java.io.IOException
import javafx.fxml.FXMLLoader
import javafx.scene.input.MouseEvent
import javafx.scene.shape.MeshView
import javafx.scene.transform.{Rotate, Translate}

import qsim.bloch.core.Bloch
import qsim.bloch.ui.XView

import scala.io.Source
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene._
import scalafx.scene.paint.{Color, PhongMaterial}


/**
  * Created by alonso on 12/06/2017.
  */

object Main extends JFXApp { app =>
  System.setProperty("prism.dirtyopts", "false")

  private var mousePosX: Double = 0
  private var mousePosY: Double = 0

  private val rotateX: Rotate = new Rotate(0, Rotate.X_AXIS)
  private val rotateY: Rotate = new Rotate(0, Rotate.Y_AXIS)
  private val rotateZ: Rotate = new Rotate(0, Rotate.Z_AXIS)

  private final val root = new Group
  private final val camera: PerspectiveCamera = new PerspectiveCamera(true)
  private final val world = new XView

  private final val sceneSize = 10
  private final val axesScaleFactor = 1.3

  // initialize bloch factory
  Bloch.init(sceneSize, axesScaleFactor)

  buildScene
  buildCamera
  buildBlochSphere
  buildAxes

  stage = new JFXApp.PrimaryStage {
    scene = new Scene(root, 1024, 768, true, SceneAntialiasing.Balanced) {
      fill = Color.Black
      title = "Bloch Sphere"
      camera = app.camera
    }

    handleMouse(scene())
  }

  private def handleMouse(scene: Scene): Unit = {
    scene.onMousePressed = (me: MouseEvent) => {
      mousePosX = me.sceneX
      mousePosY = me.sceneY
    }

    scene.onMouseDragged = (me: MouseEvent) => {
      val dx = mousePosX - me.getSceneX
      val dy = mousePosY - me.getSceneY
      if (me.isPrimaryButtonDown) {
        //rotateX.setAngle(rotateX.getAngle() -
        //(dy / sphere.getRadius() * 360) * (Math.PI / 180));
        rotateY.setAngle(rotateY.getAngle - (dx / sceneSize * -360) * (Math.PI / 180))
      }
      mousePosX = me.getSceneX
      mousePosY = me.getSceneY
    }
  }

  private def buildScene() {
    root.children += world
    world.transforms addAll(rotateZ, rotateY, rotateX)
  }

  private def buildCamera() {
    camera.getTransforms.addAll(
      new Rotate(-20, Rotate.Y_AXIS),
      new Rotate(-20, Rotate.X_AXIS),
      new Translate(0, 0, -80))
  }

  private def buildBlochSphere() {
    world.children += new XView {
      children += Bloch.buildSphere
    }
  }

  private def buildAxes() {
    world.children += new XView {
      children ++= Seq(
        Bloch buildAxis Color.Yellow,
        Bloch buildAxis(Color.Green, Rotate.X_AXIS),
        Bloch buildAxis(Color.Blue, Rotate.Z_AXIS),
        Bloch buildStateVector Color.Red
      )
    }
  }

  private def buildStateVector(): Unit = {

  }


}
