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
      case _ => "Invalid Rotation"
    }
    mower
  }

  def executeInstruction(mower: Mower, instruction: Instructions.Value): Mower = {
    instruction match {
      case 'L' => print('L')
      case 'R' => print('R')
      case 'A' => print('A')
    }
  }

  def executeInstructionList(mower: Mower, instructions: List[Instructions.Value]): MowerState = ???

  def executeHelper(mowers: Map[Mower, List[Instructions.Value]], rightTopCorner: Coordinate, value: List[Nothing]): List[MowerState] = {

  }

  def execute(mowers: Map[Mower, List[Instructions.Value]], rightTopCorner: Coordinate): List[MowerState] =  {
    executeHelper(mowers, rightTopCorner, List())
  }

}
