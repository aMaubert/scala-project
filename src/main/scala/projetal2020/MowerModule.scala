package projetal2020

import projetal2020.CoordinateModule.Coordinate
import projetal2020.ParserModule.Parser.isAllDigit
import projetal2020.ParserModule.Parser

import scala.annotation.tailrec
import scala.sys.exit

object MowerModule {
  case class Mower(point: Coordinate, direction: Direction.Value) {
    override def toString: String =
      "Mower ( point=" + point.toString + " , direction=" + direction.toString + " )"

    val _direction: Lens[Mower, Direction.Value] =
      new Lens[Mower, Direction.Value] {
        override def get: (Mower) => Direction.Value = _.direction

        override def set: (Mower, Direction.Value) => Mower = {
          case (m, dv) => m.copy(direction = dv)
        }
      }

    val _coordinate: Lens[Mower, Coordinate] = new Lens[Mower, Coordinate] {
      override def get: (Mower) => Coordinate = _.point

      override def set: (Mower, Coordinate) => Mower = {
        case (m, c) => m.copy(point = c)
      }
    }
  }

  object Mowers {
    def stringToMower(splitContent: List[String]): Mower = splitContent match {
      case Nil => exit(1)
      case head :: tail =>
        tail match {
          case Nil => exit(1)
          case head2 :: tail2 =>
            tail2 match {
              case Nil => exit(1)
              case head3 :: _ =>
                if (!isAllDigit(head.toList) || !isAllDigit(head2.toList))
                  exit(1)
                else
                  new Mower(
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
    def parserMowerHelper(
        content: List[String],
        currentValue: Map[Mower, List[Instructions.Value]]
    ): Map[Mower, List[Instructions.Value]] = content match {
      case Nil      => currentValue
      case _ :: Nil => exit(1)
      case head :: tail =>
        parserMowerHelper(
          tail.drop(1),
          currentValue.concat(
            Map(
              stringToMower(head.split(" ").toList) -> stringToInstructionList(
                tail.headOption
                  .getOrElse({
                    exit(1)
                  }),
                List()
              )
            )
          )
        )
    }

    implicit val parser: Parser[Map[Mower, List[Instructions.Value]]] =
      (content: List[String]) =>
        content match {
          case Nil       => exit(1)
          case _ :: tail => parserMowerHelper(tail, Map())
        }
  }

}
