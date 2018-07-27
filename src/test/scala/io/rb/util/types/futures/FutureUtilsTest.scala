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
          val expected = Future.successful(Right(value))
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
          val expected = Future.successful(Left(exception))
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
      val i0v = "i0v"
      val i1v = new Throwable("i1v")
      val i2v = "i0v"

      val i0 = i0v.toFuture()
      val i1 = i1v.toFuture()
      val i2 = i2v.toFuture()

      val input = Seq(i0, i1, i2)
      val expected = Future.successful(
        Seq(
          Right(i0v),
          Left(i1v),
          Right(i2v)
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
