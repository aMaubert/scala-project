package projetal2020

import org.scalatest.funsuite.AnyFunSuite
import projetal2020.CoordinateModule.Coordinate

class CoordinateParseSpec extends AnyFunSuite {

  test("Should parse string List to coordinate") {
    val coordinates = List("5 5")
    assert(Coordinate.parser.parse(coordinates) === Coordinate(5, 5))
  }

  test("Should parse string List with mowers to coordinate") {
    val coordinates = List("5 5", "1 2 N", "GAGAGAGAA")
    assert(Coordinate.parser.parse(coordinates) === Coordinate(5, 5))
  }

  test(
    "Should throw a DonneesIncorectesException when the string to parse has 1 alpha Digit"
  ) {
    assertThrows[DonneesIncorectesException] {
      Coordinate.parser.parse(List("5 Z"))
    }
  }

  test(
    "Should throw a DonneesIncorectesException when the string list is empty"
  ) {
    assertThrows[DonneesIncorectesException] {
      Coordinate.parser.parse(List())
    }
  }

  test(
    "Should throw a DonneesIncorectesException when the string to parse is empty"
  ) {
    assertThrows[DonneesIncorectesException] {
      Coordinate.parser.parse(List(""))
    }
  }

}
