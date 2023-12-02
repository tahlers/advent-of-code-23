import aoc23.Day02
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day02Test : FunSpec({

    val sample = """
Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
   """.trimIndent()


    test("calculate sum of possible game id for sample") {
        val result = Day02.sumOfPossibleGameIds(sample)
        result shouldBe 8
    }

    test("calculate calibrationSum solution one") {
        val input = this.javaClass.getResource("/day02.txt")!!.readText()
        val result = Day02.sumOfPossibleGameIds(input)
        result shouldBe 2286
    }

    test("calculate sum of power of minimal games") {
        val result = Day02.sumOfPowerOfMinimalGames(sample)
        result shouldBe 2286
    }

    test("calculate sum of power of minimal games solution") {
        val input = this.javaClass.getResource("/day02.txt")!!.readText()
        val result = Day02.sumOfPowerOfMinimalGames(input)
        result shouldBe 0
    }

})