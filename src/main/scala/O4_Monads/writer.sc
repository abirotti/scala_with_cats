import scala.language.postfixOps

object Writerz {

  def slowly[A](body: => A) =
    try body
    finally Thread.sleep(100)

  def factorial(n: Int): Int = {
    val ans = slowly(if (n == 0) 1 else n * factorial(n - 1))
    println(s"fact $n $ans")
    ans
  }

  factorial(5)

  import scala.concurrent._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  Await.result(
    Future.sequence(Vector(Future(factorial(3)), Future(factorial(3)))),
    5 seconds)

  import cats.data.Writer
  import cats.instances.vector._
  import cats.syntax.applicative._
  import cats.syntax.writer._

  type Logged[A] = Writer[Vector[String], A]

  def fact(n: Int): Logged[Int] = {
    val ans: Logged[Int] =
      if (n == 0)
        1.pure[Logged]
      else
        slowly(fact(n - 1).map(_ * n))
    ans.mapWritten(_ :+ s"fact $n ${ans.value}")
  }

  val Vector((logA, ansA), (logB, ansB)) =
    Await.result(Future.sequence(
                   Vector(
                     Future(fact(3).run),
                     Future(fact(5).run)
                   )),
                 5.seconds)
}
