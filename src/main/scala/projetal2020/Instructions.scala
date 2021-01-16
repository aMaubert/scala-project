package projetal2020

object Instructions extends Enumeration {
  val Left, Right, Forward = Value

  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  def parse(letter: Char): Instructions.Value = letter match {
    case 'G' => Left
    case 'D' => Right
    case 'A' => Forward
    case _ =>
      throw DonneesIncorectesException("Error parse Instruction .")
  }

}
