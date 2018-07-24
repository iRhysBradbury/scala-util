package io.rb.util.types.functions

import io.rb.util.types.trys.TryUtils

import scala.concurrent.Future
import scala.util.Try

trait FunctionUtils
  extends TryUtils {

  implicit class RichFunction[A, B](fn: A => B) {

    def toFuture(a: B): Future[B] = {
      Try(fn(a)).fold(Future.failed, Future.successful)
    }
  }
}
