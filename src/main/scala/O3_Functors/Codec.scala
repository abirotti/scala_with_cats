package O3_Functors

object Codec {

  trait Codec[A] {
    self =>

    def encode(value: A): String
    def decode(value: String): A

    def imap[B](dec: A => B, enc: B => A) = new Codec[B] {
      override def encode(value: B): String = self.encode(enc(value))
      override def decode(value: String): B = dec(self.decode(value))
    }
  }

  def encode[A](value: A)(implicit c: Codec[A]) = c.encode(value)
  def decode[A](value: String)(implicit c: Codec[A]) = c.decode(value)

  implicit val stringCodec: Codec[String] =
    new Codec[String] {
      override def encode(value: String): String = value
      override def decode(value: String): String = value
    }

  implicit val intCodec: Codec[Int] =
    stringCodec.imap(_.toInt, _.toString)

  implicit val doubleCodec: Codec[Double] =
    stringCodec.imap(_.toDouble, _.toString)

  case class Box[A](value: A)

  implicit def boxCodec[A](implicit codecForA: Codec[A]): Codec[Box[A]] =
    codecForA.imap(Box(_), _.value)
}
