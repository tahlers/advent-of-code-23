package aoc23

import kotlin.math.max
import kotlin.math.min

object Day13 {

    data class Pos(val x: Int, val y: Int)
    data class Pattern(val stones: Set<Pos> = emptySet()) {
        fun vertical(index: Int): Set<Pos> = stones.filter { it.x == index }.toSet()
        fun transpose() = Pattern(stones.map { Pos(it.y, it.x) }.toSet())
    }

    fun calculateOfReflectionPatterns(input: String, smudgeCount: Int = 0): Int {
        val patterns = parseInput(input)
        val patternCount = patterns.map { pattern ->
            findVerticalReflection(pattern, smudgeCount) +
                    findVerticalReflection(pattern.transpose(), smudgeCount) * 100
        }
        return patternCount.sum()
    }

    private fun findVerticalReflection(pattern: Pattern, smudgeCount: Int): Int {
        val maxX = pattern.stones.maxOf { it.x }
        val reflectionColumn = (0..<maxX).singleOrNull { col ->
            isReflectionAtCol(col, min(col + 1, maxX - col), pattern, smudgeCount)
        } ?: -1
        return reflectionColumn + 1
    }

    private fun isReflectionAtCol(col: Int, reflectionSize: Int, pattern: Pattern, smudgeCount: Int): Boolean {
        val reflectionRange = 1..reflectionSize
        val differenceCount = reflectionRange.sumOf {
            val colLeft = col - (it - 1)
            val colRight = col + it
            countYDifferences(
                pattern.vertical(colLeft),
                pattern.vertical(colRight)
            )
        }
        return differenceCount == smudgeCount
    }

    private fun countYDifferences(a: Set<Pos>, b: Set<Pos>): Int {
        val aYs = a.map { it.y }
        val bYs = b.map { it.y }
        val maxY = max(aYs.max(), bYs.max())
        return (0..maxY).count { (it in aYs) xor (it in bYs) }
    }

    private fun parseInput(input: String): List<Pattern> {
        val blocks = input.trim().split("\n\n").map { it.trim() }
        val pattern = blocks.map { block ->
            val blockLines = block.lines()
            val stones = blockLines.flatMapIndexed { y, line ->
                line.mapIndexedNotNull { x, c ->
                    if (c == '#') Pos(x, y) else null
                }
            }
            Pattern(stones.toSet())
        }
        return pattern
    }
}