package io.rb.util.types.trys

import scala.util.{Failure, Success, Try}

trait TryUtils {

  implicit class RichTry[T](tri: Try[T]) {

    def toEither(): Either[Throwable, T] = {
      tri match {
        case Success(s) => Right(s)
        case Failure(s) => Left(s)
      }
    }
  }
}
