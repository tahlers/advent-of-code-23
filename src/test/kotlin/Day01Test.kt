import aoc23.Day01
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day01Test : FunSpec({

    val sample = """
       1abc2
       pqr3stu8vwx
       a1b2c3d4e5f
       treb7uchet
   """.trimIndent()

    val sample2 = """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
    """.trimIndent()

    test("calculate calibrationSum test") {
        val result = Day01.calculateCalibrationSum(sample)
        result shouldBe 142
    }

    test("calculate calibrationSum solution one") {
        val input = this.javaClass.getResource("/day01.txt")!!.readText()
        val result = Day01.calculateCalibrationSum(input)
        result shouldBe 53386
    }

    test("calculate calibrationSum2 test") {
        val result = Day01.calculateCalibrationSum2(sample2)
        result shouldBe 281
    }

    test("calculate calibrationSum solution two") {
        val input = this.javaClass.getResource("/day01.txt")!!.readText()
        val result = Day01.calculateCalibrationSum2(input)
        result shouldBe 53312
    }
})