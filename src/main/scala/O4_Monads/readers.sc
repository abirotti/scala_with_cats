object readers {
  import cats.data.Reader

  case class Cat(name: String, favouriteFood: String)

  val catName: Reader[Cat, String] =
    Reader(cat => cat.name)

  val greetCat: Reader[Cat, String] =
    catName.map(name => s"hello $name")

  val virgil = Cat("Virgil", "mice")

  catName(virgil)
  greetCat(virgil)

  val feedCat: Reader[Cat, String] =
    Reader(cat => s"eat a nice ${cat.favouriteFood}")

  val greetAndFeed: Reader[Cat, String] =
    for {
      greet <- greetCat
      feed <- feedCat
    } yield s"$greet \n $feed"

  greetAndFeed(virgil)

  case class Db(
      usernames: Map[Int, String],
      passwords: Map[String, String]
  )

  type DBReader[A] = Reader[Db, A]

  def findUsername(user: Int): DBReader[Option[String]] =
    Reader(db => db.usernames.get(user))

  def checkPassword(
      username: String,
      password: String
  ): DBReader[Boolean] =
    Reader(db => db.passwords.get(username).contains(password))

  val db = Db(
    Map(1 -> "Andrea"),
    Map("Andrea" -> "lol")
  )

  findUsername(1)(db)
  findUsername(2)(db)

  def checkLogin(
      userId: Int,
      password: String
  ): DBReader[Boolean] =
    for {
      username <- findUsername(userId)
      pass <- username
        .map((name: String) => checkPassword(name, password))
        .getOrElse(Reader[Db, Boolean](_ => false))
    } yield pass

  checkLogin(1, "lol")(db)
  checkLogin(2, "lol")(db)
}
