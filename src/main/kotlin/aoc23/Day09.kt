package aoc23

object Day09 {

    fun calculateSumOfExtrapolatedValues(input: String): Long {
        val baseNumbers = parseInput(input)
        val generated = baseNumbers.map { generateNextValueLists(it) }
        val extrapolated = generated.map { calculateExtrapolation(it) }
        return extrapolated.sum()
    }

    fun calculateSumOfPreviousExtrapolatedValues(input: String): Long {
        val baseNumbers = parseInput(input)
        val generated = baseNumbers.map { generateNextValueLists(it) }
        val extrapolated = generated.map { calculatePreviousExtrapolation(it) }
        return extrapolated.sum()
    }

    private fun calculateExtrapolation(generated: List<List<Long>>): Long {
        val inputList = generated.asReversed().drop(1)
        return inputList.fold(0L) { acc, nextList ->
            acc + nextList.last()
        }
    }

    private fun calculatePreviousExtrapolation(generated: List<List<Long>>): Long {
        val inputList = generated.asReversed().drop(1)
        return inputList.fold(0L) { acc, nextList ->
            nextList.first() - acc
        }
    }

    private fun generateNextValueLists(base: List<Long>): List<List<Long>> {
        tailrec fun generate(current: List<List<Long>>): List<List<Long>> {
            val currentLast = current.last()
            if (currentLast.all { it == 0L }) return current
            val newLine = currentLast.windowed(2)
                .map { it[1] - it[0] }
            return generate(current + (listOf(newLine)))
        }
        return generate(listOf(base))
    }

    private fun parseInput(input: String): List<List<Long>> =
        input.trim().lines().map { line ->
            line.split(' ').map { it.toLong() }
        }
}