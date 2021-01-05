package projetal2020

import projetal2020.MowerModule.Mower

///**
// * @param start
// * @param instructions
// * @param end
// */
class MowerState(
    val start: Mower,
    val instructions: List[String],
    val end: Mower
) {

  def printableInstructionList: String = {
    val returnString = new StringBuffer
    instructions foreach { instruct =>
      returnString.append(instruct)
    } //println (key + "-->" + value)}
    returnString.toString
  }

  override def toString: String = {

    "[Start Mower : " + start.toString +
      ", Instructions List : " + printableInstructionList +
      ", End Mower : " + end.toString + "]"
  }
}
