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
  // All well, while we are doing it only once

  import scala.concurrent._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  Await.result(
    Future.sequence(Vector(Future(factorial(3)), Future(factorial(3)))),
    5 seconds)

  //Uh, which log is for which factorial???

  import cats.data.Writer
  import cats.instances.vector._
  import cats.syntax.applicative._
  import cats.syntax.writer._

  //connects the logs to the individual process, to be unfolded later upon request
  type Logged[A] = Writer[Vector[String], A]

  def fact(n: Int): Logged[Int] = {
    val ans: Logged[Int] =
      if (n == 0)
        1.pure[Logged]
      else
        slowly(fact(n - 1).map(_ * n)) // this would return an Int

    //returns a Writer[Vector[String], Int]
    ans.mapWritten(_ :+ s"fact $n ${ans.value}")

    //could also use bimap which (as usual) takes two functions to transform both the logs and the value
  }

  val Vector((logA, ansA), (logB, ansB)) =
    Await.result(Future.sequence(
                   Vector(
                     Future(fact(3).run),
                     Future(fact(5).run)
                   )),
                 5.seconds)
}
