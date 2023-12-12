import aoc23.Day12
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day12Test : FunSpec({

    val sample = """
        ???.### 1,1,3
        .??..??...?##. 1,1,3
        ?#?#?#?#?#?#?#? 1,3,1,6
        ????.#...#... 4,1,1
        ????.######..#####. 1,6,5
        ?###???????? 3,2,1
    """.trimIndent()

    test("calculate sum of arrangements of sample") {
        val result = Day12.calculateSumOfArrangements(sample)
        result shouldBe 21L
    }

    test("calculate sum of arrangements of input") {
        val input = this.javaClass.getResource("/day12.txt")!!.readText()
        val result = Day12.calculateSumOfArrangements(input)
        result shouldBe 6949L
    }

    test("calculate sum of unfolded arrangements of sample") {
        val result = Day12.calculateSumOfArrangements(sample, true)
        result shouldBe 525152L
    }

    test("calculate sum of unfolded arrangements of input") {
        val input = this.javaClass.getResource("/day12.txt")!!.readText()
        val result = Day12.calculateSumOfArrangements(input, true)
        result shouldBe 51456609952403L
    }
})