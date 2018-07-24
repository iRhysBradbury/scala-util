package io.rb.util.types.trys

import org.scalatest.{FreeSpec, Matchers}
import scala.util.{Failure, Success}

class TryUtilsTest
  extends FreeSpec
    with Matchers
    with TryUtils {

  "TryUtils" - {

    "RichUtils" - {

      "Success" in {
        val value = "value"
        val input = Success(value)
        val expected = Right(value)
        val actual = input.toEither()

        actual shouldBe expected
      }

      "Failure" in {
        val value = new Throwable
        val input = Failure(value)
        val expected = Left(value)
        val actual = input.toEither()

        actual shouldBe expected
      }
    }
  }
}
