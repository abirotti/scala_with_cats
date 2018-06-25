package O1_Introduction.exercise

import cats.Show
import org.scalatest.{FreeSpec, Matchers}

class CatTest extends FreeSpec with Matchers {

  "CatPrintable" - {
    val virgil = Cat("Virgil", 7, "red")
    val expectedString = "Virgil is a 7 year-old red cat"
    "returns a cat's expected description" in {
      Printable.format(virgil) should be(expectedString)
    }
    "does the same using the extension method" in {
      import PrintableSyntax.PrintableOpts
      virgil.format should be(expectedString)
    }
    "does the same using Cats.show" in {
      val show = Cat.oneMoreCatShow
      show.show(virgil) should be(expectedString)
    }
    "does the same using Cats' syntax" in {
      import cats.syntax.show._
      virgil.show should be(expectedString)
      implicitly[Show[Cat]] should be(Cat.oneMoreCatShow)
    }
  }
  "Cat Eq" - {
    val virgil = Cat("Virgil", 7, "red")
    val freddie = Cat("Freddie", 17, "black-and-white")
    "faulse!" in {
      virgil === freddie should be(false)
    }
    "true!" in {
      virgil === virgil should be(true)
    }
    val optionCat1 = Option(virgil)
    "should verify option equality" in {
      optionCat1 === Option(virgil)
    }
    "should verify option inequality" in {
      val optionCat2 = Option.empty[Cat]
      optionCat1 === optionCat2 should be(false)
    }
  }
}
