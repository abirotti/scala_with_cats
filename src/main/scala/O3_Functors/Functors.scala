package O3_Functors

import cats.Functor

object Functors {

  sealed trait Tree[+A]

  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  final case class Leaf[A](value: A) extends Tree[A]

  def treeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
      case Leaf(value)  => Leaf(f(value))
      case Branch(l, r) => Branch(map(l)(f), map(r)(f))
    }
  }

  trait Printable[A] {
    external =>
    def format(value: A): String

    def contramap[B](func: B => A): Printable[B] =
      (value: B) => {
        external.format(func(value))
      }
  }

  def format[A](value: A)(implicit p: Printable[A]) = p.format(value)
}
