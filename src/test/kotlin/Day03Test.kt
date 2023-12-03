import aoc23.Day03
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day03Test : FunSpec({

    val sample = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
   """.trimIndent()


    test("calculate sum of part numbers adjacent to symbols sample") {
        val result = Day03.calculateSumAdjacentToSymbols(sample)
        result shouldBe 4361
    }

    test("calculate sum of part numbers adjacent to symbols solution") {
        val input = this.javaClass.getResource("/day03.txt")!!.readText()
        val result = Day03.calculateSumAdjacentToSymbols(input)
        result shouldBe 540025
    }

    test("calculate sum of gear ratios from sample") {
        val result = Day03.calculateSumOfGearRatios(sample)
        result shouldBe 467835
    }

    test("calculate sum of gear ratios for solution") {
        val input = this.javaClass.getResource("/day03.txt")!!.readText()
        val result = Day03.calculateSumOfGearRatios(input)
        result shouldBe 84584891
    }
})