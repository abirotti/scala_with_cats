import scala.language.higherKinds

object semigroupal {

  import cats.instances.option._
  import cats.syntax.apply._

  (Option(123), Option("abc"), Some(1)).tupled

  case class Cat(name: String, born: Int, color: String)

  //There is Virgil (or was)
  (
    Option("Virgil"),
    Option(2005),
    Option("Red")
  ).mapN(Cat.apply)

  //But there is no Garfield
  (
    Option("Garfield"),
    None,
    Option("Red")
  ).mapN(Cat.apply)

  import cats.Semigroupal

  Semigroupal[Option].product(Some(1), None)
  Semigroupal[Option].product(Some(1), Some("else"))

  (Some(1), Option(2)).tupled
  (Some(1), Option("2"), Option(3L)).tupled

  (Some(1), Option(2)).mapN(_ + _)
  (Some(1), Option(2), Option(3)).mapN { (a, b, c) =>
    a + b + c
  }

  //Cat/Tuple Encoder
  case class Kat(name: String, yOB: Int, foods: List[String])

  val tupleToKat: (String, Int, List[String]) => Kat = Kat.apply

  val katToTuple: Kat => (String, Int, List[String]) = kat =>
    (kat.name, kat.yOB, kat.foods)

  val datuple = ("lal", 2012, List("Milk"))
  val dakat = tupleToKat(datuple._1, datuple._2, datuple._3)

  //back home again works a treat
  katToTuple(dakat) == datuple

  //List and Either .product are implemented in terms of their map and flatMap,
  // so they retains the sequential character of the Monad
  // Semigroupal is still useful to provide the product behaviour for data types that are not Monad.
}
