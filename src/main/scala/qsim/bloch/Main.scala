package qsim.bloch

import qsim.bloch.ui.ViewController

import scalafx.application.JFXApp
import scalafx.scene._
import scalafx.scene.paint.Color



/**
  * Created by alonso on 12/06/2017.
  */

object Main extends JFXApp { app =>
  System.setProperty("prism.dirtyopts", "false")
  private val parentScene : Group = new Group
  private val camera : PerspectiveCamera = new PerspectiveCamera(true)

  private final val sceneSize = 10
  private final val axesScaleFactor = 1.3

  this.stage = new JFXApp.PrimaryStage {
    scene = new Scene(parentScene, 1024, 768, true, SceneAntialiasing.Balanced) {
      fill = Color.Black
      title = "Bloch Sphere"
      camera = app.camera
    }

    ViewController.initScene(parentScene, sceneSize, axesScaleFactor)
    ViewController.initCamera(camera)
    ViewController.initMouseHandler(scene())
  }

  ViewController.drawBlochSphere
  ViewController.drawAxes
}
