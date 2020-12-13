package projetal2020

import scala.annotation.tailrec

object ParserModule {

  trait Parser[A] {
    def parse(content: List[String]): A
  }

  object Parser {
    def apply[A](implicit parser: Parser[A]): Parser[A] = parser

    implicit def parse[A](implicit parser: Parser[A]): Parser[A] = parser

    @tailrec
    def isAllDigit(str: List[Char]): Boolean = str match {
      case Nil          => false
      case head :: Nil  => head.isDigit
      case head :: tail => head.isDigit && isAllDigit(tail)
    }

  }
}
