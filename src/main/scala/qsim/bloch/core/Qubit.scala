package qsim.bloch.core

/**
  * Created by alonso on 18/06/2017.
  */
class Qubit(var q0: Int = 1, var q1: Int = 0)  {

  override def toString = s"Q($q0, $q1)"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Qubit]

  override def equals(other: Any): Boolean = other match {
    case that: Qubit =>
      (that canEqual this) &&
        q0 == that.q0 &&
        q1 == that.q1
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(q0, q1)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
