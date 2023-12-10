import aoc23.Day10
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day10Test : FunSpec({

    val sample1 = """
        -L|F7
        7S-7|
        L|7||
        -L-J|
        L|-JF
    """.trimIndent()

    val sample2 = """
        7-F7-
        .FJ|7
        SJLL7
        |F--J
        LJ.LJ
    """.trimIndent()

    val sample3 = """
        FF7FSF7F7F7F7F7F---7
        L|LJ||||||||||||F--J
        FL-7LJLJ||||||LJL-77
        F--JF--7||LJLJ7F7FJ-
        L---JF-JLJ.||-FJLJJ7
        |F|F-JF---7F7-L7L|7|
        |FFJF7L7F-JF7|JL---7
        7-L-JL7||F7|L7F-7F7|
        L.L7LFJ|||||FJL7||LJ
        L7JLJL-JLJLJL--JLJ.L
    """.trimIndent()

    test("calculate step count of sample") {
        val result1 = Day10.calculateStepCount(sample1)
        val result2 = Day10.calculateStepCount(sample2)
        result1 shouldBe 4
        result2 shouldBe 8
    }

    test("calculate step count of solution one") {
        val input = this.javaClass.getResource("/day10.txt")!!.readText()
        val result = Day10.calculateStepCount(input)
        result shouldBe 6823
    }

    test("calculate enclosed tiles of sample") {
        val result = Day10.calculateEnclosedTiles(sample3)
        result shouldBe 10
    }

    test("calculate enclosed tiles for solution") {
        val input = this.javaClass.getResource("/day10.txt")!!.readText()
        val result = Day10.calculateEnclosedTiles(input)
        result shouldBe 415
    }
})