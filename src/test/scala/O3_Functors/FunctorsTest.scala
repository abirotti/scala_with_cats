package O3_Functors

import O3_Functors.Functors.{Branch, Leaf, Printable, format}
import org.scalatest.{FreeSpec, Matchers}

class FunctorsTest extends FreeSpec with Matchers {

  import Functors.treeFunctor

  "tree functor" - {
    "should correctly transform a leaf" in {
      treeFunctor.map(Leaf(1))(_ + 3) should be(Leaf(4))
    }
    "should transform a more complex tree" in {
      val tree = Branch(Leaf(3), Branch(Branch(Leaf(1), Leaf(2)), Leaf(5)))
      treeFunctor.map(tree)(_ * 2) should be(
        Branch(Leaf(6), Branch(Branch(Leaf(2), Leaf(4)), Leaf(10)))
      )
    }
  }

  "Printable" - {
    "should print a string" in {
      implicit val stringPrintable: Printable[String] =
        (value: String) => "\"" + value + "\""

      format("hello") should be("\"hello\"")
    }
    "should print a boolean" - {
      implicit val booleanPrintable: Printable[Boolean] =
        (value: Boolean) => if (value) "yes" else "no"

      "as yes if true" in {
        format(true) should be("yes")
      }
      "as no if false" in {
        format(false) should be("no")
      }
    }
  }
}
