package O1_Introduction

sealed trait Json

final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(get: String) extends Json
final case class JsNumber(get: Double) extends Json
case object JsNull extends Json

//the Type Class
trait JsonWriter[A] {
  def write(value: A): Json
}

//the Type Class companion object is a good place to put an instance
//it will be found because it's now part of the implicit scope
object JsonWriter {
  implicit val stringWriter: JsonWriter[String] = (value: String) =>
    JsString(value)

  // type class instance as a def
  implicit def optionWriter[A](implicit writer: JsonWriter[A]): JsonWriter[Option[A]] = {
    (value: Option[A]) => value match {
      case Some(cont) => writer.write(cont)
      case None => JsNull
    }
  }
}

case class Person(name: String, email: String)

//need to import to pull these into scope
object JsonWriterInstances {
  implicit val personWriter: JsonWriter[Person] = { (value: Person) =>
    JsObject(
      Map("name" -> JsString(value.name), "email" -> JsString(value.email)))
  }
}

object Json {
  def toJson[A](value: A)(implicit writer: JsonWriter[A]): Json =
    writer.write(value)
}

object JsonSyntax {
  //Extension method
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit jw: JsonWriter[A]) = jw.write(value)
  }
}
