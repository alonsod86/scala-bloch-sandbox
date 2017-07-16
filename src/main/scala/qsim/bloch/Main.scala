package qsim.bloch

import javafx.application.Platform
import javafx.embed.swing.JFXPanel

import qsim.bloch.ui.ViewController

import scalafx.application.JFXApp
import scalafx.scene._
import scalafx.scene.paint.Color

// define a scala runnable
class MyThread extends Runnable {

  def run {
    Main.main(null)
  }

}

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

  def drawBlochSphere() = {
    draw(() => {
      ViewController.drawBlochSphere
      ViewController.drawAxes
    })
  }

  def start(): Unit = {
    new JFXPanel // avoid Toolkit initialization exception
    new Thread(new MyThread).start
  }

  private def draw(callback: Runnable) = {
    Platform.runLater(callback)
  }

}
