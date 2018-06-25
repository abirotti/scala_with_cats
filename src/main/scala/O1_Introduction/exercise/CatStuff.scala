package O1_Introduction.exercise

import cats.{Eq, Show}
import cats.instances.int._
import cats.instances.string._
import cats.syntax.show._

final case class Cat(name: String, age: Int, colour: String)

object Cat {
  implicit val catPrintable: Printable[Cat] = (cat: Cat) => {
    import cat._
    s"$name is a $age year-old $colour cat"
  }

//  implicit val catShow: Show[Cat] = (cat: Cat) => {
//    import cat._
//    s"$name is a $age year-old $colour cat"
//  }

  implicit val oneMoreCatShow: Show[Cat] = Show.show(cat => {
    import cat._
    s"$name is a $age year-old $colour cat"
  })

  implicit val catEq: Eq[Cat] = {
    import cats.instances.int._
    import cats.instances.string._
    import cats.syntax.eq._
    Eq.instance[Cat] { (cat1, cat2) =>
      cat1.name == cat2.name &&
      cat1.age == cat2.age &&
      cat1.colour == cat2.colour
    }
  }
}
