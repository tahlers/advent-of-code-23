import aoc23.Day08
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day08Test : FunSpec({

    val sample1 = """
        RL
        
        AAA = (BBB, CCC)
        BBB = (DDD, EEE)
        CCC = (ZZZ, GGG)
        DDD = (DDD, DDD)
        EEE = (EEE, EEE)
        GGG = (GGG, GGG)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

    val sample2 = """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

    val sample3 = """
        LR

        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
    """.trimIndent()

    test("calculate steps of sample") {
        val result1 = Day08.stepsToReachTarget(sample1)
        val result2 = Day08.stepsToReachTarget(sample2)
        result1 shouldBe 2L
        result2 shouldBe 6L
    }

    test("calculate steps of solution one") {
        val input = this.javaClass.getResource("/day08.txt")!!.readText()
        val result = Day08.stepsToReachTarget(input)
        result shouldBe 13771L
    }

    test("calculate ghost steps of sample3") {
        val result = Day08.stepsToReachGhostTarget(sample3)
        result shouldBe 6L
    }

    test("calculate ghost steps of solution two") {
        val input = this.javaClass.getResource("/day08.txt")!!.readText()
        val result = Day08.stepsToReachGhostTarget(input)
        result shouldBe 13129439557681L
    }
})