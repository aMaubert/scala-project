package projetal2020


object Parser {
  // def parse[A](content: List[String]): A  => ???
  implicit val parserCoordinate: Parser[Coordinate] = (content: List[String]) => ???
  def parser[A,B](implicit parserA: Parser[A], parserB: Parser[B]): Map[A,B] = (content: List[String]) => ???

}

trait Parser[A] {
  def parse(content: List[String]): A
}
