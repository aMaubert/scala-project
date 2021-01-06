package projetal2020

import projetal2020.ParserModule.Parser
import projetal2020.ParserModule.Parser.isAllDigit

import scala.annotation.tailrec
import scala.sys.exit

object CoordinateModule {

  case class Coordinate(x: Number, y: Number) {

    override def toString: String =
      "Coordinate( x=" + x.toString + " , y=" + y.toString + " )"
  }

  object Coordinate {

    def listToCoordinate(numbers: List[Number]): Coordinate = numbers match {
      case Nil      => exit(1)
      case _ :: Nil => exit(1)
      case head :: tail =>
        new Coordinate(tail.headOption.getOrElse({ exit(1) }), head)
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

    implicit val parser: Parser[Coordinate] = (content: List[String]) =>
      content.headOption match {
        case None        => exit(1)
        case Some(value) => helpParseCoordinate(value.split(' ').toList, List())
      }
  }
}
