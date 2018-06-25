package O2_Monoids_And_Semigroups

trait Semigroup[A] {
  def combine(x: A, y: A): A
}

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object Monoid {
  def apply[A](implicit monoid: Monoid[A]): Monoid[A] = monoid
}

object BooleanMonoids {
  val orMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    override def empty: Boolean = false

    override def combine(x: Boolean, y: Boolean): Boolean = x || y
  }

  val andMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    override def empty: Boolean = true

    override def combine(x: Boolean, y: Boolean): Boolean = x && y
  }

  val xorMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    override def empty: Boolean = false

    override def combine(x: Boolean, y: Boolean): Boolean =
      (x && !y) || (!x && y)
  }

  val nxorMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    override def empty: Boolean = true

    override def combine(x: Boolean, y: Boolean): Boolean =
      (x && y) || (!x && !y)
  }
}

object SetMonoids {
  def unionMonoid[A]: Monoid[Set[A]] = new Monoid[Set[A]] {
    override def empty: Set[A] = Set.empty[A]

    override def combine(x: Set[A], y: Set[A]): Set[A] =
      x union y
  }

  def intersectionSemigroup[A]: Semigroup[Set[A]] =
    (x: Set[A], y: Set[A]) => x intersect y

  def diffMonoid[A]: Monoid[Set[A]] = new Monoid[Set[A]] {
    override def empty: Set[A] = Set.empty[A]

    override def combine(x: Set[A], y: Set[A]): Set[A] =
      (x diff y) union (y diff x)
  }
}
