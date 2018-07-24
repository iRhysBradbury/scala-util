package io.rb.util.types.futures

import io.rb.util.types.t.TUtils
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FreeSpec, Matchers}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class FutureUtilsTest
  extends FreeSpec
    with Matchers
    with ScalaFutures
    with FutureUtils
    with TUtils {

  "FutureUtils" - {

    "RichFuture" - {

      "recoverAsTry" - {
        "Right" in {
          val value = "value"
          val input = Future.successful(value)
          val expected = Future.successful(Success(value))
          val actual = input.recoverAsTry()

          whenReady(
            for {
              e <- expected
              a <- actual
            } yield e shouldBe a
          )(identity)
        }

        "Left" in {
          val exception = new Throwable
          val input = Future.failed(exception)
          val expected = Future.successful(Failure(exception))
          val actual = input.recoverAsTry()

          whenReady(
            for {
              e <- expected
              a <- actual
            } yield e shouldBe a
          )(identity)
        }
      }
    }

    "successfulSequence" in {
      val i0 = "i0".toFuture()
      val i1 = new Throwable("i1").toFuture()
      val i2 = "i2".toFuture()

      val input = Seq(i0, i1, i2)
      val expected = Future.successful(
        Seq(
          Right(i0),
          Left(i1),
          Right(i2)
        )
      )
      val actual = FutureUtils.successfulSequence(input)

      whenReady(
        for {
          e <- expected
          a <- actual
        } yield e shouldBe a
      )(identity)
    }
  }
}
