package O2_Monoids_And_Semigroups.syntax

import cats.Monoid
import cats.instances.int._
import cats.instances.option._
import org.scalatest.{FreeSpec, Matchers}

class SuperAdderTest extends FreeSpec with Matchers{

  import SuperAdder._

  "Add" - {
    "should add an empty list" in {
      add(List()) should be(Monoid[Option[Int]].empty)
    }
    "should add a 1 item list" in {
      add(List(13).map(Some(_))) should be(Some(13))
    }
    "should add a list" in {
      add(List(1,2,3,4).map(Some(_))) should be(Some(10))
    }
  }

}
