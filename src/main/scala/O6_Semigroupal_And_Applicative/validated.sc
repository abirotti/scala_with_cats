import java.io.FileNotFoundException

import cats.Semigroupal
import cats.data.Validated

Validated.Valid(123)
Validated.Invalid(List("OMG!"))

//the smart constructors
Validated.valid[List[String], Int](123)
Validated.invalid[List[String], Int](List("OMG!"))

//syntax
import cats.syntax.validated._
123.valid[List[String]]
List("Omg!").invalid[Int]

import cats.syntax.applicative._
import cats.syntax.applicativeError._
import cats.instances.list._
//pure
type ErrorsOr[A] = Validated[List[String], A]
123.pure[ErrorsOr]
List("Omg!").raiseError[ErrorsOr, Int]

//from other types
Validated.catchOnly[FileNotFoundException](throw new FileNotFoundException("oh my!"))
Validated.fromEither(Right(1))
Validated.fromEither(Left("oh no!"))
Validated.fromOption(Some(1), "this broke!")
Validated.fromOption(None, "this broke!")

// Now for some combinations...
type AllErrorsOr[A] = Validated[String, A]
import cats.instances.string._
Semigroupal[AllErrorsOr]

import cats.syntax.apply._
(
  "Error 1".invalid[Int],
  "Error 2".invalid[Int]
).tupled

import cats.instances.list._
(
  List("big trouble").invalid[Int],
  List("another trouble").invalid[Int]
).tupled

import cats.data.NonEmptyList
(
  NonEmptyList.of("big trouble").invalid[Int],
  NonEmptyList.of("another trouble").invalid[Int],
  NonEmptyList.of("yet another one!").invalid[Int]
).tupled