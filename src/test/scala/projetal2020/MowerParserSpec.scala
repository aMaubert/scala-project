package projetal2020

import org.scalatest.funsuite.AnyFunSuite
import projetal2020.CoordinateModule.Coordinate
import projetal2020.MowerModule.{Mower, Mowers}

class MowerParserSpec extends AnyFunSuite {

  test("Should parse string List to Map of mower and instructions") {
    val input = List("5 5", "1 2 N", "GAGAGAGAA")
    val mowerToParse = Mower(Coordinate(1, 2), Direction.North)
    val instructionsToParse = List(
      Instructions.Left,
      Instructions.Forward,
      Instructions.Left,
      Instructions.Forward,
      Instructions.Left,
      Instructions.Forward,
      Instructions.Left,
      Instructions.Forward,
      Instructions.Forward
    )
    assert(
      Mowers.parser.parse(input) === Map(mowerToParse -> instructionsToParse)
    )
  }

  test("Should parse successfully more than 1 mower") {
    val input = List("5 5", "1 2 N", "GA", "3 5 S", "AAD")
    assert(
      Mowers.parser.parse(input) === Map(
        Mower(Coordinate(1, 2), Direction.North) -> List(
          Instructions.Left,
          Instructions.Forward
        ),
        Mower(Coordinate(3, 5), Direction.South) -> List(
          Instructions.Forward,
          Instructions.Forward,
          Instructions.Right
        )
      )
    )
  }

  test("Should throw a DonneesIncorectesException when parse an empty list") {
    assertThrows[DonneesIncorectesException](
      Mowers.parser.parse(List())
    )
  }

  test("Should throw a DonneesIncorectesException when parse an empty mower") {
    assertThrows[DonneesIncorectesException](
      Mowers.parser.parse(List("10 10", "", "ADD"))
    )
  }

  test(
    "Should throw a DonneesIncorectesException when parse an a wrong formated mower"
  ) {
    assertThrows[DonneesIncorectesException](
      Mowers.parser.parse(List("10 10", "N 1 2", "ADD"))
    )
  }

}
