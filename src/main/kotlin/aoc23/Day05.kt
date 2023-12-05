package aoc23

import kotlin.math.min

object Day05 {

    data class MappingRange(val destinationStart: Long, val sourceStart: Long, val length: Long)
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
        }.asSequence().flatten()
        val loc2 = seedRanges.fold(Long.MAX_VALUE) { acc, current ->
            val newLocation = almanac.gardenMaps.fold(current) { acc2, map -> map.mapInput(acc2) }
            min(acc, newLocation)
        }
        return loc2
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