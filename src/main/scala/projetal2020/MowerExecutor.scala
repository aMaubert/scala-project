package projetal2020

import projetal2020.Direction

object MowerExecutor {

  // TODO : throw exception on default
  def changeDirection(mower: Mower, rotationInstruction: Instructions.Value): Mower = {
    rotationInstruction match {
      case 'R' =>
        mower.direction match {
          case Direction.North => mower.direction = Direction.East
          case Direction.East => mower.direction = Direction.South
          case Direction.South => mower.direction = Direction.West
          case Direction.West => mower.direction = Direction.North
        }
      case 'L' =>
        mower.direction match {
          case Direction.North => mower.direction = Direction.West
          case Direction.West => mower.direction = Direction.South
          case Direction.South => mower.direction = Direction.East
          case Direction.East => mower.direction = Direction.North
        }
      case _ => print("Invalid Rotation")
    }
    mower
  }

  def forward(mower: Mower, instruction: Instructions.Value): Mower = {
    if(instruction == Instructions.Forward){
      mower.direction match {
        case Direction.North => mower.point.y = mower.point.y + 1
        case Direction.East => mower.point.x = mower.point.x + 1
        case Direction.South => mower.point.y = mower.point.y + -1 1
        case Direction.West => mower.point.x = mower.point.x + -1
      }
    }
    mower
  }

  def executeInstruction(mower: Mower, instruction: Instructions.Value): Mower = {

    if(instruction == Instructions.Right || instruction == Instructions.Left){
      changeDirection(mower, instruction)
    }

    if(instruction == Instructions.Forward){
      forward(mower, instruction)
    }
    mower
  }

  def executeInstructionList(mower: Mower, instructions: List[Instructions.Value]): MowerState = ???

  def executeHelper(mowers: Map[Mower, List[Instructions.Value]], rightTopCorner: Coordinate, value: List[Nothing]): List[MowerState] = {

  }

  def execute(mowers: Map[Mower, List[Instructions.Value]], rightTopCorner: Coordinate): List[MowerState] =  {
    executeHelper(mowers, rightTopCorner, List())
  }

}
