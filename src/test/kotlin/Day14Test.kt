import aoc23.Day14
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day14Test : FunSpec({

    val sample = """
        O....#....
        O.OO#....#
        .....##...
        OO.#O....O
        .O.....O#.
        O.#..O.#.#
        ..O..#O..O
        .......O..
        #....###..
        #OO..#....
    """.trimIndent()

    test("calculate rock weight of sample") {
        val result = Day14.calculateRockWeight(sample)
        result shouldBe 136
    }

    test("calculate rock weight of input") {
        val input = this.javaClass.getResource("/day14.txt")!!.readText()
        val result =Day14.calculateRockWeight(input)
        result shouldBe 108813
    }

    test("calculate rock load after cycles") {
        val result = Day14.calculateRockWeightCycles(sample, 1_000_000_000)
        result shouldBe 64
    }

    test("calculate rock load after cycles for solution") {
        val input = this.javaClass.getResource("/day14.txt")!!.readText()
        val result = Day14.calculateRockWeightCycles(input, 1_000_000_000)
        result shouldBe 104533
    }
})
