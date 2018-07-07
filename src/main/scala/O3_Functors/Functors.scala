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

  def format[A](value: A)(implicit p: Printable[A]): String = p.format(value)

  implicit val stringPrintable: Printable[String] = (value: String) =>
    "\"" + value + "\""
  implicit val booleanPrintable: Printable[Boolean] = (value: Boolean) =>
    if (value) "yes" else "no"

  final case class Box[A](value: A)

  implicit def boxPrintable[A](
      implicit printable: Printable[A]): Printable[Box[A]] =
    printable.contramap(_.value)
}
