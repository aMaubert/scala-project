package projetal2020

import projetal2020.CoordinateModule.Coordinate
import projetal2020.Instructions.instructionListTostringList
import projetal2020.MowerModule.Mower

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
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
        case Direction.North => {
          val toto = mower._direction.update(Direction.West)(mower)
          // println(toto)
          toto
        }
        case Direction.West  => mower._direction.update(Direction.South)(mower)
        case Direction.South => mower._direction.update(Direction.East)(mower)
        case Direction.East  => mower._direction.update(Direction.North)(mower)
      }
    case _ => exit(1)
  }

  def getDirection(mower: Mower, rightTopCorner: Coordinate): Mower =
    mower.direction match {
      case Direction.North => {
        val _mowerCoord: Lens[Mower, Number] = {
          mower._coordinate.and(mower.point._coordinateY)
        }
        if (rightTopCorner.y.intValue() != mower.point.y.intValue()) {
          println(mower.point.y.intValue().toString)

          val updatedMower =
            _mowerCoord.update(mower.point.y.intValue() + 1)(mower)
          println(updatedMower.point.y.intValue())
          updatedMower
        } else {
          mower
        }

      }
      case Direction.East => {
        val _mowerCoord: Lens[Mower, Number] =
          mower._coordinate.and(mower.point._coordinateX)
        if (rightTopCorner.x.intValue() != mower.point.x.intValue()) {
          val updatedMower =
            _mowerCoord.update(mower.point.x.intValue() + 1)(mower)
          updatedMower
        } else {
          mower
        }
      }
      case Direction.South => {
        val _mowerCoord: Lens[Mower, Number] =
          mower._coordinate.and(mower.point._coordinateY)
        if (rightTopCorner.y.intValue() != mower.point.y.intValue()) {
          val updatedMower =
            _mowerCoord.update(mower.point.y.intValue() - 1)(mower)
          updatedMower
        } else {
          mower
        }

      }
      case Direction.West => {
        println("YOOOOOO")
        val _mowerCoord: Lens[Mower, Number] =
          mower._coordinate.and(mower.point._coordinateX)
        if (rightTopCorner.x.intValue() != mower.point.x.intValue()) {
          val updatedMower =
            _mowerCoord.update(mower.point.x.intValue() - 1)(mower)
          updatedMower
        } else {
          mower
        }

      }
      case _ => exit(1)

    }

  def forward(
      mower: Mower,
      instruction: Instructions.Value,
      rightTopCorner: Coordinate
  ): Mower = instruction match {
    case Instructions.Forward => getDirection(mower, rightTopCorner)
    case _                    => exit(1)
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
      case _                    => exit(1)
    }
  }

  /*def executeInstructionList(
      mower: Mower,
      instructions: List[Instructions.Value],
      rightTopCorner: Coordinate
  ): MowerState = instructions match {
    case Nil => exit(1)
    case head :: tail => {
      println(instructions)
      val startMower = mower
      val updatedMower = executeInstruction(mower, head, rightTopCorner)
      println(updatedMower.toString)
      if (tail.nonEmpty) {
        println(head.toString)
        // println(updatedMower.toString)
        executeInstructionList(updatedMower, tail, rightTopCorner)
      } else {
        new MowerState(
          startMower,
          instructionListTostringList(tail, List()),
          updatedMower
        )
      }

    }
  }*/

  @tailrec
  def executeInstructionListHelper(
      mower: Mower,
      instructions: List[Instructions.Value],
      rightTopCorner: Coordinate
  ): Mower = instructions match {
    case Nil => exit(1)
    case head :: tail =>
      println(head)
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
    println(instructions)
    val startMower = mower
    val updatedMower =
      executeInstructionListHelper(mower, instructions, rightTopCorner)
    println(updatedMower)
    /*instructions.foreach(instruction => {
      val updatedMower2 = executeInstruction(mower, instruction, rightTopCorner)
      updatedMower2
    })*/
    new MowerState(
      startMower,
      instructionListTostringList(instructions, List()),
      updatedMower
    )

    //val updatedMower = executeInstruction(mower, head, rightTopCorner)
    //println(updatedMower.toString)
    //if (tail.nonEmpty) {
    //  println(head.toString)
    // println(updatedMower.toString)
    //  executeInstructionList(updatedMower, tail, rightTopCorner)
    //} else {
    //  new MowerState(
    //   startMower,
    // instructionListTostringList(tail, List()),
    //  updatedMower
    // )
  }

  /*def executeHelper(mowers: Map[Mower, List[Instructions.Value]], rightTopCorner: Coordinate, value: List[Nothing]): List[MowerState] = {
    val returnList = new ListBuffer[MowerState]
    mowers foreach {
      case (key, value) => returnList += executeInstructionList(key, value, rightTopCorner)
    } //println (key + "-->" + value)}
    returnList.toList
   }*/
  def executeHelper(
      mowers: Map[Mower, List[Instructions.Value]],
      rightTopCorner: Coordinate
  ): List[MowerState] = {
    val returnList = new ListBuffer[MowerState]
    mowers foreach {
      case (key, value) =>
        returnList += executeInstructionList(key, value, rightTopCorner)
    } //println (key + "-->" + value)}
    returnList.toList
  }

  def execute(
      mowers: Map[Mower, List[Instructions.Value]],
      rightTopCorner: Coordinate
  ): List[MowerState] = {
    //executeHelper(mowers, rightTopCorner, List())
    executeHelper(mowers, rightTopCorner)
  }

}
