package com.innerproduct.ee.io

import cats.effect._

object MieCazzate extends IOApp.Simple { // <1>
  def run: IO[Unit] = // <2>
    for {
        _ <- somethingFancy.debug()
    } yield()

  def somethingFancy: IO[Int] = IO.pure(3) 
}