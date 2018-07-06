package O2_Monoids_And_Semigroups.syntax

import cats.Monoid
import cats.instances.all._
import org.scalatest.{FreeSpec, Matchers}

class SuperAdderTest extends FreeSpec with Matchers{

  import SuperAdder._

  "Add Int List" - {
    "should add an empty list" in {
      add[Int](List()) should be(0)
    }
    "should add a 1 item list" in {
      add[Int](List(13)) should be(13)
    }
    "should add a list" in {
      add[Int](List(1,2,3,4)) should be(10)
    }
  }

  "Add Option Int List" - {
    "should add an empty list" in {
      add[Option[Int]](List()) should be(Monoid[Option[Int]].empty)
    }
    "should add a 1 item list" in {
      add[Option[Int]](List(13).map(Some(_))) should be(Some(13))
    }
    "should add a list" in {
      add[Option[Int]](List(1,2,3,4).map(Some(_))) should be(Some(10))
    }
  }
}
