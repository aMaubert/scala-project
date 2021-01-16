package projetal2020

import projetal2020.CoordinateModule.Coordinate
import projetal2020.MowerModule.Mower

import scala.annotation.tailrec

trait IMowerExecutor {
  val mower: Mower
  val rightTopCorner: Coordinate
  val mowerStates: List[MowerState]

  def changeDirection(
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

  def checkCollision(coordinate: Coordinate): Boolean = {
    !mowerStates.exists(m => m.end.point.equals(coordinate))
  }

  def forward(): Mower =
    mower.direction match {
      case Direction.North =>
        if (rightTopCorner.y.intValue() > mower.point.y.intValue() &&
            checkCollision(
              Coordinate(mower.point.x, mower.point.y.intValue() + 1)
            )) {

          val updatedCoord =
            new Coordinate(mower.point.x, mower.point.y.intValue() + 1)
          Mower(updatedCoord, mower.direction)
        } else {
          mower
        }
      case Direction.East =>
        if (rightTopCorner.x.intValue() > mower.point.x.intValue() &&
            checkCollision(
              Coordinate(mower.point.x.intValue() + 1, mower.point.y)
            )) {
          val updatedCoord =
            new Coordinate(mower.point.x.intValue() + 1, mower.point.y)
          Mower(updatedCoord, mower.direction)
        } else {
          mower
        }
      case Direction.South =>
        if (mower.point.y.intValue() > 0 &&
            checkCollision(
              Coordinate(mower.point.x, mower.point.y.intValue() - 1)
            )) {
          val updatedCoord =
            new Coordinate(mower.point.x, mower.point.y.intValue() - 1)
          Mower(updatedCoord, mower.direction)
        } else {
          mower
        }
      case Direction.West =>
        if (mower.point.x.intValue() > 0 &&
            checkCollision(
              Coordinate(mower.point.x.intValue() - 1, mower.point.y)
            )) {
          val updatedCoord =
            new Coordinate(mower.point.x.intValue() - 1, mower.point.y)
          Mower(updatedCoord, mower.direction)
        } else {
          mower
        }
    }

  def executeInstruction(
      instruction: Instructions.Value
  ): Mower = {
    instruction match {
      case Instructions.Right   => changeDirection(instruction)
      case Instructions.Left    => changeDirection(instruction)
      case Instructions.Forward => forward()
    }
  }

}

case class MowerExecutor(
    mower: Mower,
    rightTopCorner: Coordinate,
    mowerStates: List[MowerState]
) extends IMowerExecutor {}

object MowerExecutor {

  @tailrec
  def executeInstructionListHelper(
      mower: Mower,
      instructions: List[Instructions.Value],
      rightTopCorner: Coordinate,
      mowerStates: List[MowerState]
  ): Mower = instructions match {
    case Nil => mower
    case firstInstruction :: tailInstructions =>
      val mowerExecutor = MowerExecutor(mower, rightTopCorner, mowerStates)
      val updatedMower = mowerExecutor.executeInstruction(firstInstruction)
      executeInstructionListHelper(
        updatedMower,
        tailInstructions,
        rightTopCorner,
        mowerStates
      )
  }

  def executeInstructionList(
      mower: Mower,
      instructions: List[Instructions.Value],
      rightTopCorner: Coordinate,
      mowerStates: List[MowerState]
  ): MowerState = {
    val startMower = mower
    val updatedMower =
      executeInstructionListHelper(
        mower,
        instructions,
        rightTopCorner,
        mowerStates
      )
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
          rightTopCorner,
          mowerStates
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
