import aoc23.Day06
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day06Test : FunSpec({

    test("calculate error margin of sample") {
        val result = Day06.calculateErrorMargin(
            Day06.Race(7, 9),
            Day06.Race(15, 40),
            Day06.Race(30, 200),
        )
        result shouldBe 288
    }

    test("calculate error margin of solution one") {
        val result = Day06.calculateErrorMargin(
            Day06.Race(44, 283),
            Day06.Race(70, 1134),
            Day06.Race(70, 1134),
            Day06.Race(80, 1491),
        )
        result shouldBe 219849
    }

    test("calculate single error margin of sample") {
        val result = Day06.calculateErrorMargin(
            Day06.Race(71530, 940200),
        )
        result shouldBe 71503
    }

    test("calculate single error margin of solution two") {
        val result = Day06.calculateErrorMargin(
            Day06.Race(44707080L, 283113411341491L),
        )
        result shouldBe 29432455L
    }
})