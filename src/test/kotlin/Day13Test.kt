import aoc23.Day13
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day13Test : FunSpec({

    val sample = """
        #.##..##.
        ..#.##.#.
        ##......#
        ##......#
        ..#.##.#.
        ..##..##.
        #.#.##.#.

        #...##..#
        #....#..#
        ..##..###
        #####.##.
        #####.##.
        ..##..###
        #....#..#
    """.trimIndent()

    test("calculate reflection patterns of sample") {
        val result = Day13.calculateOfReflectionPatterns(sample)
        result shouldBe 405
    }

    test("calculate reflection patterns of input") {
        val input = this.javaClass.getResource("/day13.txt")!!.readText()
        val result = Day13.calculateOfReflectionPatterns(input)
        result shouldBe 27664
    }

    test("calculate reflection patterns with smudge of sample"){
        val result = Day13.calculateOfReflectionPatterns(sample, 1)
        result shouldBe 400
    }

    test("calculate reflection patterns with smudge of input"){
        val input = this.javaClass.getResource("/day13.txt")!!.readText()
        val result = Day13.calculateOfReflectionPatterns(input, 1)
        result shouldBe 33991
    }
})
