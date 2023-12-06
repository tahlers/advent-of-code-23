package aoc23

object Day06 {

    data class Race(val time: Long, val distance: Long)

    fun calculateErrorMargin(vararg input: Race): Long {
        val distances = input.map { it to lengthByTime(it.time) }
        val filtered = distances.map { it.second.filter { d -> d > it.first.distance } }
        return filtered.map { it.fold(0) { acc, _ -> acc + 1} }.fold(1) { acc, size -> acc * size}
    }

    private fun lengthByTime(time: Long): Sequence<Long> =
        (1 .. time).asSequence().map { (time - it) * it  }
}