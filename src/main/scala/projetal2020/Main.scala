package projetal2020

import better.files._
import projetal2020.CoordinateModule.Coordinate
import projetal2020.MowerModule.Mowers

object Main extends App {

  // 1 on va récupérer toutes les lignes du fichier
  val file: File = File("input.txt") // using constructor
  val lines = file.lines.toList

  // 2 Récupérations des instructions, tondeuses, et topRightCorner
  // On renvoie un parsor exception en case d'erreur
  val rightTopCorner = Coordinate.parser.parse(lines)
  val mowers = Mowers.parser.parse(lines)

  print("mowers : ")
  println(mowers)

  //  // 3 Executé les tondeuses
  //  val mowerStates : List[MowerState] = MowerExecutor.execute(mowers, rightTopCorner)
  //
  //  // 4 On récupère le rapport
  //  val report = new Report(rightTopCorner, mowerStates)
  //
  //  // 5 On écrit le rapport dans un fichier
  //  val outpuFile: File = File("../../output.txt") // using constructor
  //
  //  // 6
  //  //Should Beauttifer the JSON
  //  // https://jsonformatter.curiousconcept.com/
  //  //Beautiffer
  //  outpuFile.createIfNotExists()
  //    .appendLine(Report.writes.writes(report).toString())

}
