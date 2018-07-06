package O2_Monoids_And_Semigroups.syntax

import cats.Monoid
import cats.instances.int._
import org.scalatest.{FreeSpec, Matchers}

class SuperAdderTest extends FreeSpec with Matchers{

  import O2_Monoids_And_Semigroups.syntax.SuperAdder._

  "Add" - {
    "should add an empty list" in {
      add(List()) should be(Monoid[Int].empty)
    }
    "should add a 1 item list" in {
      add(List(13)) should be(13)
    }
    "should add a list" in {
      add(List(1,2,3,4)) should be(10)
    }
  }

}
