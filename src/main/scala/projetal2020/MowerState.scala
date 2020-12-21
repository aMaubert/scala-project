package projetal2020

/**
 * {
 * "debut": {
 * "point": {
 * "x": 1,
 * "y": 2
 * },
 * "direction": "N"
 * },
 * "instructions": ["G","A","G","A","G","A","G","A","A"],
 * "fin": {
 * "point": {
 * "x": 1,
 * "y": 3
 * },
 * "direction": "N"
 * }
 * }
 *
 * @param start
 * @param instructions
 * @param end
 */
class MowerState(
    val start: Mower,
    val instructions: List[String],
    val end: Mower
) {}
