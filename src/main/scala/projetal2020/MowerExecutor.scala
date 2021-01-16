package projetal2020

import projetal2020.CoordinateModule.Coordinate
import projetal2020.MowerModule.Mower

import scala.annotation.tailrec

object MowerExecutor {

  def changeDirection(
      mower: Mower,
      rotationInstruction: Instructions.Value
  ): Mower = rotationInstruction match {
    case Instructions.Right =>
      mower.direction match {
        case Direction.North =>
          Mower(mower.point, Direction.East)
        case Direction.East =>
          Mower(mower.point, Direction.South)
        case Direction.South =>
          Mower(mower.point, Direction.West)
        case Direction.West =>
          Mower(mower.point, Direction.North)
      }
    case Instructions.Left =>
      mower.direction match {
        case Direction.North =>
          Mower(mower.point, Direction.West)
        case Direction.West =>
          Mower(mower.point, Direction.South)
        case Direction.South =>
          Mower(mower.point, Direction.East)
        case Direction.East =>
          Mower(mower.point, Direction.North)
      }
  }

  def getDirection(mower: Mower, rightTopCorner: Coordinate): Mower =
    mower.direction match {
      case Direction.North =>
        if (rightTopCorner.y.intValue() > mower.point.y.intValue()) {
          val updatedCoord =
            new Coordinate(mower.point.x, mower.point.y.intValue() + 1)
          Mower(updatedCoord, mower.direction)
        } else {
          mower
        }
      case Direction.East =>
        if (rightTopCorner.x.intValue() > mower.point.x.intValue()) {
          val updatedCoord =
            new Coordinate(mower.point.x.intValue() + 1, mower.point.y)
          Mower(updatedCoord, mower.direction)
        } else {
          mower
        }
      case Direction.South =>
        if (mower.point.y.intValue() > 0) {
          val updatedCoord =
            new Coordinate(mower.point.x, mower.point.y.intValue() - 1)
          Mower(updatedCoord, mower.direction)
        } else {
          mower
        }
      case Direction.West =>
        if (mower.point.x.intValue() > 0) {
          val updatedCoord =
            new Coordinate(mower.point.x.intValue() - 1, mower.point.y)
          Mower(updatedCoord, mower.direction)
        } else {
          mower
        }
    }

  def forward(
      mower: Mower,
      instruction: Instructions.Value,
      rightTopCorner: Coordinate
  ): Mower = instruction match {
    case Instructions.Forward => getDirection(mower, rightTopCorner)
  }

  def executeInstruction(
      mower: Mower,
      instruction: Instructions.Value,
      rightTopCorner: Coordinate
  ): Mower = {
    instruction match {
      case Instructions.Right   => changeDirection(mower, instruction)
      case Instructions.Left    => changeDirection(mower, instruction)
      case Instructions.Forward => forward(mower, instruction, rightTopCorner)
    }
  }

  @tailrec
  def executeInstructionListHelper(
      mower: Mower,
      instructions: List[Instructions.Value],
      rightTopCorner: Coordinate
  ): Mower = instructions match {
    case Nil => mower
    case head :: tail =>
      val updatedMower = executeInstruction(mower, head, rightTopCorner)
      if (tail.nonEmpty) {
        executeInstructionListHelper(updatedMower, tail, rightTopCorner)
      } else {
        updatedMower
      }
  }

  def executeInstructionList(
      mower: Mower,
      instructions: List[Instructions.Value],
      rightTopCorner: Coordinate
  ): MowerState = {
    val startMower = mower
    val updatedMower =
      executeInstructionListHelper(mower, instructions, rightTopCorner)
    new MowerState(
      startMower,
      instructions,
      updatedMower
    )
  }

  @tailrec
  def executeHelper(
      mowers: Map[Mower, List[Instructions.Value]],
      rightTopCorner: Coordinate,
      mowerStates: List[MowerState]
  ): List[MowerState] = mowers.toList match {
    case Nil => mowerStates
    case head :: tail =>
      executeHelper(
        tail.toMap,
        rightTopCorner,
        mowerStates :+ executeInstructionList(
          head._1,
          mowers(head._1),
          rightTopCorner
        )
      )
  }

  def execute(
      mowers: Map[Mower, List[Instructions.Value]],
      rightTopCorner: Coordinate
  ): List[MowerState] = {
    executeHelper(mowers, rightTopCorner, List())
  }

}
