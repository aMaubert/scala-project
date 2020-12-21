package projetal2020

case class Coordinate(val x: Number, val y: Number) {

  val _coordinateX = new Lens[Coordinate, Number] {
    override def get: (Coordinate) => Number = _.x

    override def set: (Coordinate, Number) => Coordinate = {
      case (c, n) => c.copy(x = n)
    }
  }

  val _coordinateY = new Lens[Coordinate, Number] {
    override def get: (Coordinate) => Number = _.y

    override def set: (Coordinate, Number) => Coordinate = {
      case (c, n) => c.copy(y = n)
    }
  }

}
