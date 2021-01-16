package projetal2020

import play.api.libs.json.{Json, Writes}
import projetal2020.MowerModule.{Mower}

///**
// * @param start
// * @param instructions
// * @param end
// */
case class MowerState(
    start: Mower,
    instructions: List[Instructions.Value],
    end: Mower
) {}

object MowerState {
  implicit val jsonWriter: Writes[MowerState] = Json.writes[MowerState]
}
