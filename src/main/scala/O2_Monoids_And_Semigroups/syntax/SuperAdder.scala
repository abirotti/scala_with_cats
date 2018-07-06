package O2_Monoids_And_Semigroups.syntax

import cats.Monoid
import cats.instances.int._
import cats.instances.option._
import cats.syntax.semigroup._

object SuperAdder {

  def add(items: List[Option[Int]]): Option[Int] =
    items.foldRight(Monoid[Option[Int]].empty) { (i, acc) =>
      acc |+| i
    }
}
