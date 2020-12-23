package projetal2020

import scala.sys.exit

object MowerExecutor {

  // TODO : throw exception on default instead of exit ?
  def changeDirection(
      mower: Mower,
      rotationInstruction: Instructions.Value
  ): Mower = rotationInstruction match {
    case Instructions.Right =>
      mower.direction match {
        case Direction.North => mower._direction.update(Direction.East)(mower)
        case Direction.East  => mower._direction.update(Direction.South)(mower)
        case Direction.South => mower._direction.update(Direction.West)(mower)
        case Direction.West  => mower._direction.update(Direction.North)(mower)
      }
    case Instructions.Left =>
      mower.direction match {
        case Direction.North => mower._direction.update(Direction.West)(mower)
        case Direction.West  => mower._direction.update(Direction.South)(mower)
        case Direction.South => mower._direction.update(Direction.East)(mower)
        case Direction.East  => mower._direction.update(Direction.North)(mower)
      }
    case _ => exit(1)
  }

  def getDirection(mower: Mower): Mower = mower.direction match {
    case Direction.North => {
      val _mowerCoord: Lens[Mower, Number] =
        mower._coordinate.and(mower.point._coordinateX)
      val y = mower.point._coordinateY.get.toString()
      val updatedMower = _mowerCoord.update(y.toInt + 1)(mower)
      updatedMower
    }
    case Direction.East => {
      val _mowerCoord: Lens[Mower, Number] =
        mower._coordinate.and(mower.point._coordinateX)
      val x = mower.point._coordinateX.get.toString()
      val updatedMower = _mowerCoord.update(x.toInt + 1)(mower)
      updatedMower
    }
    case Direction.South => {
      val _mowerCoord: Lens[Mower, Number] =
        mower._coordinate.and(mower.point._coordinateX)
      val y = mower.point._coordinateY.get.toString()
      val updatedMower = _mowerCoord.update(y.toInt - 1)(mower)
      updatedMower
    }
    case Direction.West => {
      val _mowerCoord: Lens[Mower, Number] =
        mower._coordinate.and(mower.point._coordinateX)
      val x = mower.point._coordinateX.get.toString()
      val updatedMower = _mowerCoord.update(x.toInt - 1)(mower)
      updatedMower
    }
    case _ => exit(1)

  }

  def forward(mower: Mower, instruction: Instructions.Value): Mower =
    instruction match {
      case Instructions.Forward => getDirection(mower)

      case _ => exit(1)
    }

  def executeInstruction(mower: Mower, instruction: Instructions.Value): Mower =
    instruction match {
      case Instructions.Right   => changeDirection(mower, instruction)
      case Instructions.Left    => changeDirection(mower, instruction)
      case Instructions.Forward => forward(mower, instruction)
      case _                    => exit(1)

    }

   /*def executeInstructionList(mower: Mower, instructions: List[Instructions.Value]): MowerState = instructions match {
     case Nil => exit(1)
     case head :: tail => executeInstruction(mower, head)
   }*/

  //def executeHelper(mowers: Map[Mower, List[Instructions.Value]], rightTopCorner: Coordinate, value: List[Nothing]): List[MowerState] = {}

  /* def execute(mowers: Map[Mower, List[Instructions.Value]], rightTopCorner: Coordinate): List[MowerState] =  {
    executeHelper(mowers, rightTopCorner, List())
  }
 */
}
