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
}
