import aoc23.Day05
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day05Test : FunSpec({

    val sample = """
seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4
   """.trimIndent()


    test("calculate lowest location of sample") {
        val result = Day05.calculateLowestLocationNumber(sample)
        result shouldBe 35L
    }

    test("calculate lowest location of solution one") {
        val input = this.javaClass.getResource("/day05.txt")!!.readText()
        val result = Day05.calculateLowestLocationNumber(input)
        result shouldBe 177942185L
    }

    test("calculate lowest location of seed ranges of sample") {
        val result = Day05.calculateLowestSeedRangeLocationNumber(sample)
        result shouldBe 46L
    }

    test("calculate lowest location of seed ranges for solution two") {
        val input = this.javaClass.getResource("/day05.txt")!!.readText()
        val result = Day05.calculateLowestSeedRangeLocationNumber(input)
        result shouldBe 69841803L
    }
})