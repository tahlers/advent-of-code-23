import aoc23.Day16
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day16Test : FunSpec({

    val sample = """
        .|...\....
        |.-.\.....
        .....|-...
        ........|.
        ..........
        .........\
        ..../.\\..
        .-.-/..|..
        .|....-|.\
        ..//.|....
    """.trimIndent()

    test("calculate energized tiles of sample") {
        val result = Day16.calculateEnergizedTiles(sample)
        result shouldBe 46
    }

    test("calculate energized tiles of input") {
        val input = this.javaClass.getResource("/day16.txt")!!.readText()
        val result = Day16.calculateEnergizedTiles(input)
        result shouldBe 6361
    }

    test("calculate max energized tiles of sample") {
        val result = Day16.calculateMaxEnergizedTiles(sample)
        result shouldBe 51
    }

    test("calculate max energized tiles of input") {
        val input = this.javaClass.getResource("/day16.txt")!!.readText()
        val result = Day16.calculateMaxEnergizedTiles(input)
        result shouldBe 6701
    }
})
