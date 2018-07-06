package O2_Monoids_And_Semigroups.syntax

import cats.Monoid
import cats.instances.int._
import cats.syntax.semigroup._

object SuperAdder {

  def add(items: List[Int]): Int = items.foldRight(Monoid[Int].empty) {
    (i, acc) =>
      acc |+| i
  }
}
