package projetal2020

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

}
