import cats.Applicative

object traverse {

  import scala.language.higherKinds
  import cats.syntax.apply._
  import cats.syntax.applicative._

  def listTraverse[F[_]: Applicative, A, B]
  (list: List[A])(func: A => F[B]): F[List[B]] =
    list.foldLeft(List.empty[B].pure[F]) { (accum, item) =>
      (accum, func(item)).mapN(_ :+ _)
    }

  def listSequence[F[_]: Applicative, B]
  (list: List[F[B]]): F[List[B]] =
    listTraverse(list)(identity)


  import cats.instances.vector._
  listSequence(List(Vector(1, 2), Vector(3, 4), Vector(5, 6)))

  import cats.instances.option._
  def process(inputs: List[Int]) =
    listTraverse(inputs)(n => if(n%2 == 0) Some(n) else None)

  process(List(2,4,6))
  process(List(1,3,5))
  process(List(1,2,3))
}