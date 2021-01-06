package projetal2020

import projetal2020.ParserModule.Parser
import projetal2020.ParserModule.Parser.isAllDigit

import scala.annotation.tailrec

object CoordinateModule {

  case class Coordinate(x: Number, y: Number) {

    override def toString: String =
      "Coordinate( x=" + x.toString + " , y=" + y.toString + " )"

    val _coordinateX: Lens[Coordinate, Number] = new Lens[Coordinate, Number] {
      override def get: (Coordinate) => Number = _.x

      override def set: (Coordinate, Number) => Coordinate = {
        case (c, n) => c.copy(x = n)
      }
    }

    val _coordinateY: Lens[Coordinate, Number] = new Lens[Coordinate, Number] {
      override def get: (Coordinate) => Number = _.y

      override def set: (Coordinate, Number) => Coordinate = {
        case (c, n) => c.copy(y = n)
      }
    }
  }

  object Coordinate {
    @SuppressWarnings(Array("org.wartremover.warts.Throw"))
    def listToCoordinate(numbers: List[Number]): Coordinate = numbers match {
      case Nil => throw DonneesIncorectesException("Il doit y avoir 2 nombres")
      case _ :: Nil =>
        throw DonneesIncorectesException("Il doit y avoir 2 nombres")
      case head :: tail =>
        new Coordinate(tail.headOption.getOrElse({
          throw DonneesIncorectesException("Il doit y avoir 2 nombres")
        }), head)
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

    @SuppressWarnings(Array("org.wartremover.warts.Throw"))
    implicit val parser: Parser[Coordinate] = (content: List[String]) =>
      content.headOption match {
        case None =>
          throw DonneesIncorectesException(
            "La list de string ne doit pas être vide ."
          )
        case Some(value) => helpParseCoordinate(value.split(' ').toList, List())
      }
  }
}
