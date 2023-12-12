package aoc23

import arrow.core.MemoizedDeepRecursiveFunction
import kotlin.math.min

object Day12 {

    data class Row(val springs: String, val defects: List<Int>)

    fun calculateSumOfArrangements(input: String, unfolded: Boolean = false): Long {
        val rows = parseInput(input, unfolded)
        val arrangements = rows.map { countArrangements(it.springs, it.defects) }
        return arrangements.sum()
    }

    private fun countArrangements(springs: String, defects: List<Int>): Long {
        val memoized = MemoizedDeepRecursiveFunction<Pair<String, List<Int>>, Long> { (springs, defects) ->
            if (springs.isEmpty()) {
                return@MemoizedDeepRecursiveFunction if (defects.isEmpty()) 1 else 0
            }
            if (defects.isEmpty()) {
                return@MemoizedDeepRecursiveFunction if (springs.contains('#')) 0 else 1
            }
            if (springs.first() == '.') {
                return@MemoizedDeepRecursiveFunction callRecursive(springs.substring(1) to defects)
            }
            if (springs.length < (defects.sum() + defects.size - 1)) return@MemoizedDeepRecursiveFunction 0
            if (springs.first() == '#') {
                val currentRun = defects.first()
                val runSplice = springs.substring(0..<currentRun)
                if (runSplice.contains('.')) return@MemoizedDeepRecursiveFunction 0
                if (springs.length > currentRun && springs[currentRun] == '#') return@MemoizedDeepRecursiveFunction 0
                val remaining = springs.substring(min(springs.length, currentRun + 1))
                return@MemoizedDeepRecursiveFunction callRecursive(remaining to defects.drop(1))
            }
            callRecursive('.' + springs.substring(1) to defects) +
                    callRecursive('#' + springs.substring(1) to defects)

        }
        return memoized(springs to defects)
    }

    private fun parseInput(input: String, unfolded: Boolean = false): List<Row> {
        val lines = input.trim().lines()
        return lines.map { line ->
            val springs = line.substringBefore(' ')
            val defects = line.substringAfter(' ').split(',').map { it.toInt() }
            val row = Row(springs, defects)
            if (unfolded) unfoldRow(row) else row
        }
    }

    private fun unfoldRow(row: Row): Row {
        val springs = "${row.springs}?${row.springs}?${row.springs}?${row.springs}?${row.springs}"
        val defects = generateSequence { row.defects }.take(5).flatten().toList()
        return Row(springs, defects)
    }
}