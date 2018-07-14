object Evalz {
  def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B = as match {
    case head :: tail => fn(head, foldRight(tail, acc)(fn))
    case Nil          => acc
  }

  import cats.Eval

  def foldR[A, B](as: List[A], acc: B)(fn: (A, B) => B): Eval[B] =
    as match {
      case head :: tail =>
        Eval.defer(foldR(tail, acc)(fn)).map(fn(head, _))
      case Nil => Eval.now(acc)
    }


//  foldRight((1 to 100000).toList, 0L)(_ + _)
  foldR((1 to 100000).toList, 0L)(_ + _).value

}
