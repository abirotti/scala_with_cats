package O1_Introduction

import O1_Introduction.Json.toJson
import org.scalatest.{FreeSpec, Matchers}

class JsonTest extends FreeSpec with Matchers {
  import JsonWriterInstances._

  val person = Person("Andrea", "lol@really.it")

  "toJson" - {
    "should create a Person" in {
      toJson(person) should be(
        JsObject(Map("name" -> JsString("Andrea"),
                     "email" -> JsString("lol@really.it"))))
    }
    "should create the same Person as with the explicit writer" in {
      toJson(person) should be(toJson(person)(personWriter))
    }

  }
  "type class syntax" - {
    import JsonSyntax._
    "should create the same Person" in {
      person.toJson should be(toJson(person))
    }
    "implicitly find the desired implicit" in {
      implicitly[JsonWriter[Person]] should be(JsonWriterInstances.personWriter)
      implicitly[JsonWriter[String]] should be(JsonWriter.stringWriter)
    }
  }
  "leverage implicit scope" - {
    "should find the implicit in the companion object" in {
      Json.toJson("I am a person, too!") should be(JsString("I am a person, too!"))
    }
  }
  "a writer of an option" - {
    "should correctly write the content of the Option" in {
//      val optionWriter = implicitly[JsonWriter[Option[Person]]]
      //Unfortunately, we have to hint the type. Why?
      toJson(Some(person): Option[Person]) should be(toJson(person))
    }
  }
}
