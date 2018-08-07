import cats.Monad
import cats.implicits._

import scala.language.higherKinds

def product[M[_]: Monad, A, B](x: M[A], y: M[B]): M[(A, B)] =
  for {
    a <- x
    b <- y
  } yield (a, b)

val what = product(Option(1), Option("one"))
