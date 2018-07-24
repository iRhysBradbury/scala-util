package io.rb.util.types.eithers

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

trait EitherUtils {

  implicit class RichEitherThrowable[L <: Throwable, R](e: Either[L, R]) {

    def withThrowableToTry(): Try[R] = e.fold(Failure.apply, Success.apply)

    def withThrowableToFuture(): Future[R] = e.fold(Future.failed, Future.successful)
  }
}