package projetal2020

import scala.annotation.tailrec
import scala.sys.exit

object Instructions extends Enumeration {
  val Left, Right, Forward = Value

  def parse(letter: Char): Instructions.Value = letter match {
    case 'G' => Left
    case 'D' => Right
    case 'A' => Forward
    case _ =>
      print("letter : ")
      println(letter)
      exit(1)
  }

  def unParse(instructions: Instructions.Value): String = instructions match {
    case Left    => "G"
    case Right   => "D"
    case Forward => "A"
    case _ =>
      print("instructions : ")
      println(instructions)
      exit(1)
  }

  @tailrec
  def instructionListTostringList(
      content: List[Instructions.Value],
      currentValue: List[String]
  ): List[String] = content match {
    case Nil => currentValue
    case head :: tail =>
      instructionListTostringList(
        tail,
        currentValue :+ Instructions.unParse(head)
      )
  }
}
