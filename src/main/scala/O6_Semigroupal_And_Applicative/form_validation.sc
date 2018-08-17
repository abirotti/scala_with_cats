object form_validation {

  import scala.util.Try

  case class User(name: String, age: Int)

  type ErrorsOr[A] = Either[List[String], A]

  def getValue(key: String)(map: Map[String, String]): ErrorsOr[String] =
    map.get(key).toRight(List("key not specified"))

  import cats.syntax.either._

  def parseInt(string: String): ErrorsOr[Int] =
    Try(string.toInt).toEither.leftMap(_ => s"`$string` not an integer" :: Nil)

  //TODO push IDEA to see the truth of this
  def notBlank(string: String): ErrorsOr[String] =
    Right(string)
      .ensure(List(s"cannot have an empty name"))(_.nonEmpty)

  def nonNegative(age: Int): ErrorsOr[Int] =
    Right(age)
      .ensure(List("cannot have negative age"))(_ > 0)

}


def readName(map: Map[String, String])(name: String): form_validation.ErrorsOr[String] =
  for {
    daName <- form_validation.getValue(name)(map)
    name <- form_validation.notBlank(daName)
  } yield name

val simplestMap = Map("a" -> "", "b" -> "lol")
readName(simplestMap)("a")

def readAge(map: Map[String, String])(name: String): form_validation.ErrorsOr[Int] =
  for {
    daAge <- form_validation.getValue(name)(map)
    daIntAge <- form_validation.parseInt(daAge)
    age <- form_validation.nonNegative(daIntAge)
  } yield age

readAge(simplestMap)("b")

import cats.data.Validated
import cats.syntax.apply._
import cats.instances.list._

(
  Validated.fromEither(readName(simplestMap)("a")),
  Validated.fromEither(readAge(simplestMap)("b"))
).tupled

List(readName(simplestMap)("a"), readAge(simplestMap)("b"), readAge(simplestMap)("b"))
  .map(Validated.fromEither)
  .reduce {
    (a, b) =>
      (a, b).tupled
  }