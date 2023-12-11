import aoc23.Day11
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day11Test : FunSpec({

    val sample = """
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#.....
    """.trimIndent()

    test("calculate sum of shortest paths of sample") {
        val result = Day11.calculateSumOfShortestPaths(sample)
        result shouldBe 374L
    }

    test("calculate sum of shortest paths of solution one") {
        val input = this.javaClass.getResource("/day11.txt")!!.readText()
        val result = Day11.calculateSumOfShortestPaths(input)
        result shouldBe 9724940L
    }

    test("calculate sum of shortest paths with bigger expansion of sample") {
        val result1 = Day11.calculateSumOfShortestPaths(sample, 10)
        val result2 = Day11.calculateSumOfShortestPaths(sample, 100)
        result1 shouldBe 1030L
        result2 shouldBe 8410L
    }

    test("calculate sum of shortest paths with expansion 1000000 of solution one") {
        val input = this.javaClass.getResource("/day11.txt")!!.readText()
        val result = Day11.calculateSumOfShortestPaths(input, 1000000)
        result shouldBe 569052586852L
    }
})