package O2_Monoids_And_Semigroups.syntax

import cats.Monoid
import cats.instances.all._
import cats.syntax.semigroup._

object SuperAdder {

  def add[A](items: List[A])(implicit monoid: Monoid[A]): A =
    items.foldLeft(Monoid[A].empty)(Monoid[A].combine)
}
§