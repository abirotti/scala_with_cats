package O4_Monads

import cats.Eval

import scala.language.higherKinds

trait Monad[F[_]] {
  def pure[A](a: A): F[A]

  def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]

  def map[A, B](value: F[A])(func: A => B): F[B] =
    flatMap(value)(func.andThen(pure))
}

object Id {
  import cats.Id // type Id[A] = A

  def pure[A](value: A): Id[A] = value
  def map[A, B](initial: Id[A])(func: A => B): Id[B] = func(initial)
  def flatMap[A, B](initial: Id[A])(func: A => Id[B]): Id[B] =
    map(initial)(func)
}

object Evalz {

  //TODO make the following stack-safe using the Eval monad
  def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B = as match {
    case head :: tail => fn(head, foldRight(tail, acc)(fn))
    case Nil          => acc
  }

  def foldR[A, B](as: List[A], acc: B)(fn: (A, B) => B): Eval[B] =
    as match {
      case head :: tail =>
        Eval.defer(foldR(tail, acc)(fn)).map(fn(head, _))
      case Nil => Eval.now(acc)
    }

}
