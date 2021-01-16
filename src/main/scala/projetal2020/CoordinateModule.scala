package projetal2020

import play.api.libs.json.{Json, Writes}
import projetal2020.ParserModule.Parser
import projetal2020.ParserModule.Parser.isAllDigit

import scala.annotation.tailrec

object CoordinateModule {

  case class Coordinate(x: Int, y: Int) {

    override def toString: String =
      "Coordinate( x=" + x.toString + " , y=" + y.toString + " )"
  }

  object Coordinate {

    implicit val writes: Writes[Coordinate] = Json.writes[Coordinate]

    @SuppressWarnings(Array("org.wartremover.warts.Throw"))
    def listToCoordinate(numbers: List[Int]): Coordinate = numbers match {
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
        numbers: List[Int]
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
