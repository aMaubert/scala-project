package projetal2020

object Direction extends Enumeration {
  val North, East, West, South = Value

  def parse(letter: Char): Direction.Value = letter match {
    case 'N' => North
    case 'E' => East
    case 'W' => West
    case 'S' => South
  }
}
