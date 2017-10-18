package qsim.bloch.ui

import javafx.geometry.Point3D
import javafx.scene
import javafx.scene.input.MouseEvent
import javafx.scene.transform.{Rotate, Translate}

import qsim.bloch.core.operators.{Axes, BlochAxis}
import qsim.bloch.ui.components.Bloch

import scalafx.Includes._
import scalafx.scene._
import scalafx.scene.paint.Color

/**
  * Created by alonso on 15/06/2017.
  */
object ViewController {
  private var sceneSize: Double = 0
  private var mousePosX: Double = 0
  private var mousePosY: Double = 0

  private val rotateX: Rotate = new Rotate(0, Rotate.X_AXIS)
  private val rotateY: Rotate = new Rotate(0, Rotate.Y_AXIS)
  private val rotateZ: Rotate = new Rotate(0, Rotate.Z_AXIS)

  private var root : Group = null
  private var worldView : View = null

  def initScene(rootScene: Group, sceneSize: Double, axesScaleFactor: Double) = {
    // initialize components
    Bloch.init(sceneSize, axesScaleFactor)

    // build basic scene with camera and mouse
    this.root = rootScene
    this.sceneSize = sceneSize
    this.worldView = new View

    buildScene()
  }

  def initCamera(camera: Camera) {
    camera.getTransforms.addAll(
      new Rotate(-20, Rotate.Y_AXIS),
      new Rotate(-20, Rotate.X_AXIS),
      new Translate(0, 0, -80))
  }

  def initMouseHandler(sc: scene.Scene) = {
    sc.onMousePressed = (me: MouseEvent) => {
      mousePosX = me.sceneX
      mousePosY = me.sceneY
    }

    sc.onMouseDragged = (me: MouseEvent) => {
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

  def drawBlochSphere() {
    worldView.children += new View {
      children += Bloch.buildSphere
    }
  }

  def drawAxes() {
    worldView.children += new View {
      children ++= Seq(
        Bloch buildAxis(Color.Yellow, Axes.Z_AXIS),
        Bloch buildAxis(Color.Green, Axes.Y_AXIS),
        Bloch buildAxis(Color.Blue, Axes.X_AXIS)
      )
    }
  }

  def drawStateVector(color: Color, axis: BlochAxis = null): Group = {
    val vector = Bloch buildStateVector(color, axis)
    worldView.children += new View {
      children += vector
    }
    vector
  }

  def rotateStateVector(vector: Group, theta: Double): Group = {
    // TODO: refactor. X_AXIS fixed rotation...
    val rotated = Bloch rotateStateVector(vector, Axes.X_AXIS, theta)
    rotated
  }

  private def buildScene() {
    root.children += worldView
    worldView.transforms addAll(rotateZ, rotateY, rotateX)
  }

  def world():View = this.worldView

  /** Translates from scalaFX axes to qsim axes (the standard system) */
  def translateAxes(fxAxis: Point3D): BlochAxis = {
    // Z_AXIS = y
    // Y_AXIS = z
    // X_AXIS = x

    if (fxAxis.equals(Rotate.X_AXIS)) {
      Axes.X_AXIS
    } else if (fxAxis.equals(Rotate.Y_AXIS)) {
      Axes.Z_AXIS
    } else if (fxAxis.equals(Rotate.Z_AXIS)) {
      Axes.Y_AXIS
    } else {
      Axes.NONE
    }
  }

  def translateAxes(qsimAxis: BlochAxis): Point3D = {
    // Z_AXIS = y
    // Y_AXIS = z
    // X_AXIS = x

    if (qsimAxis.equals(Axes.X_AXIS)) {
      Rotate.X_AXIS
    } else if (qsimAxis.equals(Axes.Y_AXIS)) {
      Rotate.Z_AXIS
    } else if (qsimAxis.equals(Axes.Z_AXIS)) {
      Rotate.Y_AXIS
    } else {
      null
    }
  }
}
