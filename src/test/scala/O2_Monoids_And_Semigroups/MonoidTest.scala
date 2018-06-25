package O2_Monoids_And_Semigroups

import org.scalatest.{FreeSpec, Matchers}

//TODO implement tests for all implemented monoids, the laws are down here
//bonus point - do it with properties
class MonoidTest extends FreeSpec with Matchers {
  def associativeLaw[A](x: A, y: A, z: A)(implicit m: Monoid[A]): Boolean = {
    m.combine(x, m.combine(y, z)) ==
      m.combine(m.combine(x, y), z)
  }

  def identityLaw[A](x: A)(implicit m: Monoid[A]): Boolean = {
    (m.combine(x, m.empty) == x) &&
    (m.combine(m.empty, x) == x)
  }
}
