package projetal2020

//import play.api.libs.json.{Json, Writes}
import projetal2020.CoordinateModule.Coordinate

class Report(val limit: Coordinate, val mowersStates: List[MowerState]) {}

object Report {
  //implicit val writes: Writes[Report] = Json.writes[Report]
}
