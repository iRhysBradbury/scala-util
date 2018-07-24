package io.rb.util.types.eithers

import org.scalatest.{FreeSpec, Matchers}

import scala.concurrent.Future
import scala.util.{Failure, Success}

class EitherUtilsTest 
  extends FreeSpec
    with Matchers
    with EitherUtils {
  
  "EitherUtils" - {

    "RichEither" - {

      "withThrowableToTry" - {

        "Failure" in {
          val input = new Throwable
          val actual = Left(input).withThrowableToTry
          val expected = Failure(input)

          actual shouldBe expected
        }

        "Success" in {
          val input = "Success"
          val actual = Right(input).withThrowableToTry
          val expected = Success(input)

          actual shouldBe expected
        }
      }

      "withThrowableToFuture" - {

        "Failure" in {
          val input = new Throwable
          val actual = Left(input).withThrowableToFuture
          val expected = Future.failed(input)

          actual shouldBe expected
        }

        "Success" in {
          val input = "Success"
          val actual = Right(input).withThrowableToFuture
          val expected = Future.successful(input)

          actual shouldBe expected
        }
      }
    }
  }
}
