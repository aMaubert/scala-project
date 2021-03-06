package projetal2020

import play.api.libs.json.{Json, Writes}
import projetal2020.CoordinateModule.Coordinate
import projetal2020.ParserModule.Parser.isAllDigit
import projetal2020.ParserModule.Parser

import scala.annotation.tailrec

object MowerModule {

  case class Mower(point: Coordinate, direction: Direction.Value) {
    override def toString: String =
      "Mower ( point=" + point.toString + " , direction=" + direction.toString + " )"
  }

  object Mower {

    implicit val writes: Writes[Mower] = Json.writes[Mower]

    @SuppressWarnings(Array("org.wartremover.warts.Throw"))
    def stringToMower(splitContent: List[String]): Mower = splitContent match {
      case Nil => throw DonneesIncorectesException("List Should not be empty")
      case head :: tail =>
        tail match {
          case Nil =>
            throw DonneesIncorectesException(
              "List should have more than 1 element"
            )
          case head2 :: tail2 =>
            tail2 match {
              case Nil =>
                throw DonneesIncorectesException(
                  "List should have more than 2 elements"
                )
              case head3 :: _ =>
                if (!isAllDigit(head.toList) || !isAllDigit(head2.toList))
                  throw DonneesIncorectesException(
                    "Le 1er  et 2ème éléments devraient être des nombres"
                  )
                else
                  Mower(
                    new Coordinate(head.toInt, head2.toInt),
                    Direction.parse(head3.charAt(0))
                  )
            }
        }
    }

    @tailrec
    def stringToInstructionList(
        content: String,
        currentValue: List[Instructions.Value]
    ): List[Instructions.Value] = content.toList match {
      case Nil => currentValue
      case head :: tail =>
        stringToInstructionList(
          tail.mkString(""),
          currentValue :+ Instructions.parse(head)
        )
    }

    @tailrec
    @SuppressWarnings(Array("org.wartremover.warts.Throw"))
    def parserMowerHelper(
        content: List[String],
        currentValue: Map[Mower, List[Instructions.Value]]
    ): Map[Mower, List[Instructions.Value]] = content match {
      case Nil      => currentValue
      case _ :: Nil => throw DonneesIncorectesException("Can't parse mower")
      case head :: tail =>
        parserMowerHelper(
          tail.drop(1),
          currentValue.concat(
            Map(
              stringToMower(head.split(" ").toList) -> stringToInstructionList(
                tail.headOption
                  .getOrElse({
                    throw DonneesIncorectesException("Can't parse mower")
                  }),
                List()
              )
            )
          )
        )
    }
    @SuppressWarnings(Array("org.wartremover.warts.Throw"))
    implicit val parser: Parser[Map[Mower, List[Instructions.Value]]] =
      (content: List[String]) =>
        content match {
          case Nil       => throw DonneesIncorectesException("boooom")
          case _ :: tail => parserMowerHelper(tail, Map())
        }
  }

}
