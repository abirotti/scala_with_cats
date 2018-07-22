object semigroupal {

  import cats.instances.option._
  import cats.syntax.apply._

  (Option(123), Option("abc"), Some(1)).tupled

  case class Cat(name: String, born: Int, color: String)

  //There is Virgil (or was :( )
  (
    Option("Virgil"),
    Option(2005),
    Option("Red")
  ).mapN(Cat.apply)

  //There is no Garfield!
  (
    Option("Garfield"),
    None,
    Option("Red")
  ).mapN(Cat.apply)
}
