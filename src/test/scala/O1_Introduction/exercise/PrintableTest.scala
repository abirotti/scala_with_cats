package O1_Introduction.exercise

import org.scalatest.{FreeSpec, Matchers}

class PrintableTest extends FreeSpec with Matchers {

  "Printable.format" - {
    "returns a String input" in {
      import O1_Introduction.exercise.PrintableInstances._
      val input = "I am printable!"
      Printable.format(input) should be(input)
    }
    "returns a String of the given int" in {
      import O1_Introduction.exercise.PrintableInstances._
      Printable.format(123) should be("123")
    }
  }
}
