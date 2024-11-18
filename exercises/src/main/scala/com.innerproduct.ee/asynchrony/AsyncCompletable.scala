package com.innerproduct.ee.asynchrony

import cats.effect._
import java.util.concurrent.CompletableFuture
import scala.jdk.FunctionConverters._

object AsyncCompletable extends IOApp.Simple {
  def run: IO[Unit] =
    effect.debug().void

  val effect: IO[String] =
    fromCF(IO(cf()))

  def fromCF[A](cfa: IO[CompletableFuture[A]]): IO[A] =
    cfa.flatMap { fa =>
      IO.async_ { cb =>
        val handler: (A, Throwable) => Unit = {
          case (a, null) => cb(Right(a))
          case (null, t) => cb(Left(t))
          case (a, t) => sys.error(s"cippa $a $t")
        }

        fa.handle(handler.asJavaBiFunction) // <2>

        ()
      }
    }

  def cf(): CompletableFuture[String] =
    CompletableFuture.supplyAsync(() => "dd") // <3>
}
