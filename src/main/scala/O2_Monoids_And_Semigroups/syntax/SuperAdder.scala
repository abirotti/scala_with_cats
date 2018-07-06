package O2_Monoids_And_Semigroups.syntax

import cats.Monoid

object SuperAdder {

  def add[A](items: List[A])(implicit monoid: Monoid[A]): A =
    items.foldLeft(Monoid[A].empty)(Monoid[A].combine)

  implicit def orderMonoid: Monoid[Order] = new Monoid[Order] {
    override def empty: Order = Order(0d, 0d)

    override def combine(x: Order, y: Order): Order =
      Order(x.totalCost + y.totalCost, x.quantity + y.quantity)
  }
}

case class Order(totalCost: Double, quantity: Double)
