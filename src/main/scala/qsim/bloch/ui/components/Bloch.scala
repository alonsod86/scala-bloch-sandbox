package qsim.bloch.ui.components

import javafx.fxml.FXMLLoader
import javafx.geometry.Point3D
import javafx.scene.Group
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.{Cylinder, MeshView}
import javafx.scene.transform.Rotate

import scalafx.scene.paint.Color
import scalafx.scene.shape.{DrawMode, Sphere}


/**
  * Created by alonso on 12/06/2017.
  */
object Bloch {
  private val w : Double = .005 // width
  private var l : Double = 1    // length
  private var r : Double = 10   // radius

  def init(radius: Double, axesScale: Double) = {
    r = radius
    l = 2 * axesScale
  }

  /**
    * Build a semi-transparent bloch sphere
    * @return
    */
  def buildSphere: Sphere = {
    val material = new PhongMaterial
    material.setDiffuseColor(Color.Wheat)

    val s = new Sphere(r)
    s setDrawMode DrawMode.Line
    s setMaterial material
    s
  }

  /**
    * Build an axis inside the bloch sphere
    * @param color
    * @param axis
    * @return
    */
  def buildAxis(color: Color, axis: Point3D = null) = {
    val vector = new Group
    val length = l * r
    val width = w * length

    // axis
    val ax = new Cylinder(width, length)
    val material = new PhongMaterial
    material.setDiffuseColor(color)
    ax.setMaterial(material)

    applyOrthogonalTransformation(axis, vector)

    vector.getChildren.addAll(ax)
    vector
  }

  /**
    * Build a state vector in the specified axis
    * @param color
    * @param axis
    * @return
    */
  def buildStateVector(color: Color, axis: Point3D = null) = {
    val vector = new Group
    val length = r
    val width = w * length * 3

    // axis
    val ax = new Cylinder(width, length)
    val material = new PhongMaterial
    material.setDiffuseColor(color)
    ax.setMaterial(material)
    ax.setTranslateY(-length / 2)

    // pointer
    val pointer = buildArrow
    pointer.setTranslateY(-length)
    pointer.setMaterial(material)

    // rotation over base axis
    applyOrthogonalTransformation(axis, vector)

    vector.getChildren.addAll(ax, pointer)
    vector
  }

  /**
    * Axis arrow builder
    */
  private def buildArrow() = {
    val fxmlLoader = new FXMLLoader
    fxmlLoader.setLocation(getClass.getResource("/pyramid.fxml"))
    fxmlLoader.load.asInstanceOf[MeshView]
  }

  /**
    * Given a vector, applies a specific rotation to build an orthogonal final state
    * @param axis
    * @param vector
    * @return
    */
  private def applyOrthogonalTransformation(axis: Point3D, vector: Group) = {
    if (axis != null) {
      val rotation = new Rotate
      rotation.setAxis(axis)
      rotation.setAngle(90)
      vector.getTransforms.add(rotation)
    }
  }

}
