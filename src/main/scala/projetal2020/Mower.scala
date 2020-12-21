package projetal2020

case class Mower(val point: Coordinate, val direction: Direction.Value) {

  val _direction = new Lens[Mower, Direction.Value] {
    override def get: (Mower) => Direction.Value = _.direction

    override def set: (Mower, Direction.Value) => Mower = {
      case (m, dv) => m.copy(direction = dv)
    }
  }

  val _coordinate = new Lens[Mower, Coordinate] {
    override def get: (Mower) => Coordinate = _.point

    override def set: (Mower, Coordinate) => Mower = {
      case (m, c) => m.copy(point = c)
    }
  }
}
