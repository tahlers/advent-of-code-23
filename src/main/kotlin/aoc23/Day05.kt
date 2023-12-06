package aoc23

import kotlin.math.max
import kotlin.math.min

fun LongRange.overlap(range: LongRange): List<LongRange?> {
    val before = if (range.first < first) {
        range.first..(min(range.last, first - 1))
    } else null
    val after = if (range.last > last) {
        max(range.first, last + 1)..range.last
    } else null
    val between = if (range.last >= first && range.first < (last + 1)) {
        max(range.first, first)..min(range.last, last)
    } else null
    return listOf(before, between, after)
}

object Day05 {

    data class MappingRange(val destinationStart: Long, val sourceStart: Long, val length: Long) {
        private val currentRange = (sourceStart)..<(sourceStart + length)
        fun mapRange(range: LongRange): List<LongRange?> {
            val overlaps = currentRange.overlap(range)
            val newBetween = if (overlaps[1] != null) {
                val offset = destinationStart - sourceStart
                overlaps[1]!!.first + offset..(overlaps[1]!!.last + offset)
            } else null
            return listOf(overlaps[0], newBetween, overlaps[2])
        }
    }

    data class GardenMap(val name: String, val mappingRanges: List<MappingRange> = emptyList()) {

        fun addMappingRange(mappingRange: MappingRange): GardenMap =
            GardenMap(name, mappingRanges + mappingRange)

        fun mapInput(input: Long): Long {
            val mappingToUse = mappingRanges.firstOrNull {
                input in it.sourceStart..<(it.sourceStart + it.length)
            }
            return if (mappingToUse != null) {
                val offset = mappingToUse.destinationStart - mappingToUse.sourceStart
                input + offset
            } else input
        }

        fun mapRange(range: LongRange): List<LongRange> {
            val initialUnfinishedAndFinished = listOf(range) to emptyList<LongRange>()
            val folded = mappingRanges.fold(initialUnfinishedAndFinished) { acc, mappingRange ->
                val mapped = acc.first.map { mappingRange.mapRange(it) }
                val newFinished = mapped.mapNotNull { it[1] }
                val newUnfinished = mapped.flatMap { listOfNotNull(it[0], it[2]) }
                newUnfinished to (acc.second + newFinished)
            }
            return folded.first + folded.second
        }
    }

    data class Almanac(val seeds: List<Long>, val gardenMaps: List<GardenMap>)

    fun calculateLowestLocationNumber(input: String): Long {
        val almanac = parseInputToAlmanac(input)
        val locations = almanac.gardenMaps.fold(almanac.seeds) { acc, gardenMap ->
            acc.map { gardenMap.mapInput(it) }
        }
        return locations.min()
    }

    fun calculateLowestSeedRangeLocationNumber(input: String): Long {
        val almanac = parseInputToAlmanac(input)
        val seedRanges = almanac.seeds.windowed(2, step = 2) {
            (it.first())..<(it.first() + it.last())
        }
        val mappedRanges = seedRanges.flatMap { seedRange ->
            val result = almanac.gardenMaps.fold(listOf(seedRange)) { acc, gardenMap ->
                acc.flatMap { gardenMap.mapRange(it) }
            }
            result
        }
        return mappedRanges.minOf { it.first }
    }

    private fun parseInputToAlmanac(input: String): Almanac {
        val seedLine = input.trim().lineSequence().first().substringAfter(':').trim()
        val seeds = seedLine.split(' ').map { it.toLong() }
        val mapLines = input.trim().lines().drop(2)
        val init = emptyList<GardenMap>()
        val gardenMaps = mapLines.fold(init) { acc, line ->
            if (line.trim().isEmpty()) {
                acc
            } else if (line.contains(':')) {
                val newGardenMap = GardenMap(line.substringBefore(" map:"))
                acc + newGardenMap
            } else {
                val (dest, source, length) = line.trim().split(' ').map { it.toLong() }
                val mappingRange = MappingRange(dest, source, length)
                val updatedMap = acc.last().addMappingRange(mappingRange)
                acc.dropLast(1) + updatedMap
            }
        }
        return Almanac(seeds, gardenMaps)
    }
}