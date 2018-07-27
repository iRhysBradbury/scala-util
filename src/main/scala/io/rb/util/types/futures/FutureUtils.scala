package io.rb.util.types.futures

import scala.annotation.tailrec
import scala.concurrent.{ExecutionContext, Future}

trait FutureUtils {

  implicit class RichFuture[T](f: Future[T]) {

    def recoverAsTry()(implicit ec: ExecutionContext): Future[Either[Throwable, T]] = {
      f.map(r => Right(r)).recover { case l: Throwable => Left(l) }
    }
  }
}

object FutureUtils
  extends FutureUtils {

  @tailrec
  final def successfulSequence[T](
    toProcess: Seq[Future[T]],
    processed: Future[Seq[Either[Throwable, T]]] = Future.successful(Nil)
  )(
   implicit ec: ExecutionContext
  ): Future[Seq[Either[Throwable, T]]] = {
    if (toProcess.isEmpty) {
      processed
    } else {
      successfulSequence(
        toProcess = toProcess.tail,
        processed = processed.flatMap { s: Seq[Either[Throwable, T]] =>
          toProcess.head.recoverAsTry().map(s ++ Seq(_))
        }
      )
    }
  }
}