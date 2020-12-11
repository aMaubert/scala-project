package projetal2020

import scala.annotation.tailrec
import scala.sys.exit

object Parser {
  def apply[A](implicit parser: Parser[A]): Parser[A] = parser

  def listToCoordinate(numbers: List[Number]): Coordinate = numbers match {
    case Nil      => exit(1)
    case _ :: Nil => exit(1)
    case head :: tail =>
      new Coordinate(tail.headOption.getOrElse({ exit(1) }), head)
  }

  @tailrec
  def isAllDigit(str: List[Char]): Boolean = str match {
    case Nil          => false
    case head :: Nil  => head.isDigit
    case head :: tail => head.isDigit && isAllDigit(tail)
  }

  @tailrec
  def helpParseCoordinate(
      split: List[String],
      numbers: List[Number]
  ): Coordinate = split match {
    case Nil => listToCoordinate(numbers)
    case head :: tail =>
      if (head != " " && isAllDigit(head.toList))
        helpParseCoordinate(tail, head.toInt :: numbers)
      else helpParseCoordinate(tail, numbers)
  }

  implicit val parserCoordinate: Parser[Coordinate] = (content: List[String]) =>
    content.headOption match {
      case None        => exit(1)
      case Some(value) => helpParseCoordinate(value.split(' ').toList, List())
    }

  @tailrec
  def parserMowerHelper(
      content: List[String],
      currentValue: Map[Mower, List[Instructions.Value]]
  ): Map[Mower, List[Instructions.Value]] = content match {
    case Nil       => currentValue
    case _ :: tail => parserMowerHelper(tail, currentValue)
  }

  implicit val parserMower: Parser[Map[Mower, List[Instructions.Value]]] =
    (content: List[String]) =>
      content match {
        case Nil => exit(1)
        case _ :: tail => parserMowerHelper(tail, Map())
      }
}

trait Parser[A] {
  def parse(content: List[String]): A
}
