package O3_Functors

import O3_Functors.Codec.doubleCodec
import org.scalatest.{FreeSpec, Matchers}

class CodecTest extends FreeSpec with Matchers{

  "DoubleCodec" - {
    "should decode a String to Double" in {
      val doubleString = "0.32"
      doubleCodec.decode(doubleString) should be(0.32)
    }
    "should encode a Double to String" in {
      doubleCodec.encode(3.14) should be("3.14")
    }
  }


}
