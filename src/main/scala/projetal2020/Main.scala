package projetal2020

import better.files._

object Main extends App {
  println("Ici le programme principal")

  // 1 on va récupérer toutes les lignes du fichier
  val file: File = File("../../input.txt") // using constructor
  val lines = file.lines.toList


  // 2 Récupérations des instructions, tondeuses, et topRightCorner
  // On renvoie un parsor exception en case d'erreur
  val rightTopCorner = Parser[Coordinate].parse(lines)
  val mowers = Parser[Map[Mower, List[Instructions.Value] ]].parse(lines)

  // 3 Executé les tondeuses
  val mowerStates : List[MowerState] = MowerExecutor.execute(mowers, rightTopCorner)

  // 4 On récupère le rapport
  val report = new Report(rightTopCorner, mowerStates)

  // 5 On écrit le rapport dans un fichier
  val outpuFile: File = File("../../output.txt") // using constructor

  //
  //Should Beauttifer the JSON
  // https://jsonformatter.curiousconcept.com/
  //Beautiffer
  outpuFile.createIfNotExists()
    .appendLine(Report.writes.writes(report).toString())

}
