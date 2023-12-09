import aoc23.Day09
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day09Test : FunSpec({

    val sample = """
    0 3 6 9 12 15
    1 3 6 10 15 21
    10 13 16 21 30 45
    """.trimIndent()

    test("calculate sum of extrapolated values of sample") {
        val result = Day09.calculateSumOfExtrapolatedValues(sample)
        result shouldBe 114L
    }

    test("calculate sum of extrapolated values of solution one") {
        val input = this.javaClass.getResource("/day09.txt")!!.readText()
        val result = Day09.calculateSumOfExtrapolatedValues(input)
        result shouldBe 1757008019L
    }

    test("calculate sum of extrapolated previous values of sample") {
        val result = Day09.calculateSumOfPreviousExtrapolatedValues(sample)
        result shouldBe 2L
    }

    test("calculate sum of extrapolated previous of solution one") {
        val input = this.javaClass.getResource("/day09.txt")!!.readText()
        val result = Day09.calculateSumOfPreviousExtrapolatedValues(input)
        result shouldBe 995L
    }
})