object foldable {
  val someListToFold = (1 until 10).toList

  someListToFold.foldLeft(List.empty[Int])((acc, el) => el :: acc)
  someListToFold.foldRight(List.empty[Int])((el, acc) => el :: acc)

  def mapByFoldRight[A, B](list: List[A])(f: A => B): List[B] =
    list.foldRight(List.empty[B])((el, acc) => f(el) :: acc)

  mapByFoldRight(someListToFold)(a => a * 2)

  def flatMapByFoldRight[A, B](list: List[A])(f: A => List[B]): List[B] =
    list.foldRight(List.empty[B])((el, acc) => f(el) ++ acc)

  flatMapByFoldRight(someListToFold)(a => List(a * 2))

  def filterByFoldRight[A](list: List[A])(furqaan: A => Boolean): List[A] =
    list.foldRight(List.empty[A])((el, acc) =>
      if (furqaan(el)) el :: acc else acc)

  filterByFoldRight(someListToFold)(_ % 2 == 0)

  def sumByFoldRight(list: List[Int]): Int =
    list.foldRight(0)((el, acc) => el + acc)

  sumByFoldRight(someListToFold)
}
