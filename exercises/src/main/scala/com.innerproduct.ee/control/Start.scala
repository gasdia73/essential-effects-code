package com.innerproduct.ee.control

import cats.effect._
import com.innerproduct.ee.debug._

object Start extends IOApp.Simple {

  def run: IO[Unit] =
    for {
      _ <- task.start // <1>
      _ <- debugWithThread("task was started")// <2>
    } yield ()

  val task: IO[String] =
    debugWithThread("task") // <2>
}
