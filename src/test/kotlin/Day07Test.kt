import aoc23.Day07
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day07Test : FunSpec({

    val sample = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()

    test("calculate total winnings of sample") {
        val result = Day07.calculateTotalWinnings(sample)
        result shouldBe 6440L
    }

    test("calculate total winnings of solution one") {
        val input = this.javaClass.getResource("/day07.txt")!!.readText()
        val result = Day07.calculateTotalWinnings(input)
        result shouldBe 253933213L
    }

    test("calculate total winnings of joker sample") {
        val result = Day07.calculateTotalWinnings(sample, true)
        result shouldBe 5905L
    }

    test("calculate total winnings of joker solution one") {
        val input = this.javaClass.getResource("/day07.txt")!!.readText()
        val result = Day07.calculateTotalWinnings(input, true)
        result shouldBe 253473930L
    }
})