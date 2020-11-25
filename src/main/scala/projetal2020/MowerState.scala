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
class MowerState(var start: Mower, var instructions: List[String], var end: Mower) {

}
