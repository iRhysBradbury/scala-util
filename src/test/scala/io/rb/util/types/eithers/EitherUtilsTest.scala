package io.rb.util.types.eithers

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FreeSpec, Matchers}
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global


class EitherUtilsTest 
  extends FreeSpec
    with Matchers
    with ScalaFutures
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

          intercept[Throwable] {
            whenReady(
              for {
                a <- actual
              } yield ()
            )(identity)
          }
        }

        "Success" in {
          val input = "Success"
          val actual = Right(input).withThrowableToFuture
          val expected = Future.successful(input)

          whenReady(
            for {
              a <- actual
              e <- expected
            } yield a shouldBe e
          )(identity)
        }
      }
    }
  }
}
