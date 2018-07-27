package io.rb.util.types.t

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FreeSpec, Matchers}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class TUtilsTest
  extends FreeSpec
    with Matchers
    with ScalaFutures
    with TUtils {

  "TUtils" - {

    "RichT" - {

      "toOption" - {
        "Some" in {
          val input = "value"
          val expected = Some(input)
          val actual = input.toOption()

          expected shouldBe actual
        }
      }

      "toEither" - {

        "Right" in {
          val input = "value"
          val expected = Right(input)
          val actual = input.toEither()

          expected shouldBe actual
        }

        "Left" in {
          val input = new Throwable
          val expected = Left(input)
          val actual = input.toEither()

          expected shouldBe actual
        }
      }

      "toFuture" - {

        "Future.successful" in {
          val input = "value"
          val expected = Future.successful(input)
          val actual = input.toFuture()

          whenReady(
            for {
              e <- expected
              a <- actual
            } yield e shouldBe a
          )(identity)
        }

        "Future.failed" in {
          val input = new Throwable
          val expected = Future {
            throw input
          }
          val actual = input.toFuture()
          intercept[Throwable] {
            whenReady(
              for {
                e <- expected
                a <- actual
              } yield ()
            )(identity)
          }
        }
      }
    }
  }
}
