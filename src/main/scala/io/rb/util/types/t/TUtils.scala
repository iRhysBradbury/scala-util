package io.rb.util.types.t

import scala.concurrent.Future

trait TUtils {

  implicit class RichT[T](t: T) {

    def toOption(): Option[T] = Some(t)

    def toEither(): Either[Throwable, T] = {
      t match {
        case t: Throwable => Left(t)
        case _ => Right(t)
      }
    }

    def toFuture(): Future[T] = {
      t match {
        case t: Throwable => Future.failed(t)
        case _ => Future.successful(t)
      }
    }
  }
}

