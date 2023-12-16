import aoc23.Day15
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day15Test : FunSpec({

    val sample = """
        rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
    """.trimIndent()

    test("calculate hash sum of sample") {
        val result = Day15.calculateHashSum(sample)
        result shouldBe 1320
    }

    test("calculate hash sum of input") {
        val input = this.javaClass.getResource("/day15.txt")!!.readText()
        val result = Day15.calculateHashSum(input)
        result shouldBe 516657
    }

    test("calculate focusing power of sample"){
        val result = Day15.calculateFocusingPower(sample)
        result shouldBe 145
    }

    test("calculate focusing power of input"){
        val input = this.javaClass.getResource("/day15.txt")!!.readText()
        val result = Day15.calculateFocusingPower(input)
        result shouldBe 210906
    }
})
