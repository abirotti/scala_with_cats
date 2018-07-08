package O3_Functors

import O3_Functors.Codec.{Box, doubleCodec}
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
  "BoxCodec" - {
    "should encode a Box of double to String" in {
      Codec.boxCodec.encode(Box(123.4)) should be("123.4")
    }
    "should decode a String to Box[Double]" in {
      Codec.boxCodec.decode("123.4") should be(Box(123.4))
    }
  }

}
