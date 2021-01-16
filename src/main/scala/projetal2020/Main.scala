package projetal2020

import better.files._
import play.api.libs.json.Json
import projetal2020.CoordinateModule.Coordinate
import projetal2020.MowerModule.Mower

object Main extends App {

  // 1 on va récupérer toutes les lignes du fichier

  val fileName = sys.env("MOWER_INPUT")
  val file: File = File(fileName) // using constructor
  val lines = file.lines.toList

  // 2 Récupération des instructions, tondeuses, et topRightCorner
  // On renvoie une exception lors d'un cas dérreur dans le parseur
  val rightTopCorner = Coordinate.parser.parse(lines)
  val mowers = Mower.parser.parse(lines)

  // 3 Executé  les instructions pour chaque tondeuse de manière consécutive
  val mowerStates: List[MowerState] =
    MowerExecutor.execute(mowers, rightTopCorner)

  // 4 On récupère le rapport
  val report = new Report(rightTopCorner, mowerStates)

  //  5 On ouvre un fichier JSON de sortis
  val outputFileName = sys.env("MOWER_OUTPUT")
  val outputFile: File = File(outputFileName) // using constructor

  // 6 On écrit le rapport dans le fichier
  outputFile
    .createIfNotExists()
    .overwrite(Json.prettyPrint(Report.writes.writes(report)))

}
