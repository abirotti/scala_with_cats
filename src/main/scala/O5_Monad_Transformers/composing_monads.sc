import cats.data.EitherT

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps

object compo {
//  type Response[A] = Future[Either[String, A]]
  type Response[A] = EitherT[Future, String, A]

  val powerLevels = Map(
    "Jazz" -> 6,
    "Bumblebee" -> 8,
    "Hot Rod" -> 10
  )

  def getPowerLevel(autobot: String): Response[Int] =
    EitherT(Future.apply(powerLevels.get(autobot) match {
      case None        => Left(s"bot not found: $autobot")
      case Some(value) => Right(value)
    }))

  import cats.implicits._

  Await.result(getPowerLevel("Jazz").value, Duration.Inf)
  Await.result(getPowerLevel("FTF").value, Duration.Inf)

  def canSpecialMove(ally1: String, ally2: String): Response[Boolean] =
    for {
      pl1 <- getPowerLevel(ally1)
      pl2 <- getPowerLevel(ally2)
    } yield (pl1 + pl2) > 15

  Await.result(canSpecialMove("Jazz", "Bumblebee").value, Duration.Inf)
  Await.result(canSpecialMove("Jazz", "Hot Rod").value, Duration.Inf)
  Await.result(canSpecialMove("Lol", "Hot Rod").value, Duration.Inf)

  def tacticalReport(a1: String, a2: String): String =
    Await.result(canSpecialMove(a1, a2).value, 1 second) match {
      case Left(m) => m
      case Right(value) if value =>
        s"Yes, we can!!! $a1 and $a2 can do awesomes together"
      case _ => s"nah no luck, $a1 and $a2 suck together!"
    }

  tacticalReport("Jazz", "Lol")
  tacticalReport("Jazz", "Bumblebee")
  tacticalReport("Jazz", "Hot Rod")

}
