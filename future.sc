// futures for composing asynchronous computations

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

def isPrime(n: Int) = ! (2 to n / 2).exists(n % _ == 0)

val candidates = Seq(1003, 10003, 100003, 1000003)
val futures: Seq[Future[(Int, Boolean)]] =
  candidates map { n => Future { (n, isPrime(n)) } }
val f1: Future[Seq[(Int, Boolean)]] =
  Future.traverse(futures)(identity) // can now operate asynchronously
val f2: Future[Seq[Int]] =           // on values wrapped in future
  f1 map { _ filter { _._2 } map { _._1 } }
val f3: Future[Int] = f2 map { _.sum }

f3 foreach println // <--- what does this print? :)
Await.ready(f3, 2.seconds)