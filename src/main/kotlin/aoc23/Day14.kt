package aoc23

import io.vavr.collection.LinkedHashMap
import kotlin.math.min

object Day14 {

    data class Pos(val x: Int, val y: Int)
    data class MirrorMap(val maxX: Int, val maxY: Int, val rocks: Set<Pos>, val cubes: Set<Pos>) {

        fun turn(): MirrorMap {
            val turnedRocks = rocks.map { Pos(it.y, maxX + 1 - it.x) }
            val turnedCubes = cubes.map { Pos(it.y, maxX + 1 - it.x) }
            return MirrorMap(maxY, maxX, turnedRocks.toSet(), turnedCubes.toSet())
        }

        fun load(): Int = rocks.sumOf { it.y }
    }

    fun calculateRockWeight(input: String): Int {
        val mirrorMap = parseMap(input)
        val tilted = tiltNorth(mirrorMap)
        return tilted.load()
    }

    fun calculateRockWeightCycles(input: String, cycles: Int): Int {
        val mirrorMap = parseMap(input)
        val target = runCycles(0, mirrorMap, LinkedHashMap.empty(), cycles)
        val load = target.rocks.map { it.y }
        return load.sum()
    }

    private tailrec fun runCycles(
        currentCount: Int,
        mirrorMap: MirrorMap,
        visited: LinkedHashMap<MirrorMap, Int> = LinkedHashMap.empty(),
        targetCount: Int
    ): MirrorMap {
        if (currentCount == targetCount) return mirrorMap
        val newCount = currentCount + 1
        val newMirrorMap = doCycle(mirrorMap)
        val foundOption = visited.get(newMirrorMap)
        if (foundOption.isEmpty) {
            return runCycles(newCount, newMirrorMap, visited.put(newMirrorMap, newCount), targetCount)
        } else {
            val cycleStart = foundOption.get()
            println("cycle detected from $cycleStart to $newCount")
            val offset = (targetCount - newCount) % (newCount - cycleStart)
            val final = visited.toSet().first { it._2 == cycleStart + offset }._1
            return final
        }
    }

    private fun doCycle(mirrorMap: MirrorMap): MirrorMap {
        val north = tiltNorth(mirrorMap).turn()//.also { printMap(it.turn().turn().turn(), "after north") }
        val west = tiltNorth(north).turn()//.also { printMap(it.turn().turn(), "after west") }
        val south = tiltNorth(west).turn()//.also { printMap(it.turn(), "after south") }
        val east = tiltNorth(south).turn()//.also { printMap(it, "after east") }
        return east
    }

    private fun tiltNorth(mirrorMap: MirrorMap): MirrorMap {
        val newRockPos = mirrorMap.rocks.map { targetPos(it, mirrorMap) }
        return MirrorMap(mirrorMap.maxX, mirrorMap.maxY, newRockPos.toSet(), mirrorMap.cubes)//.also { printMap(it) }
    }

    private fun targetPos(rockPos: Pos, mirrorMap: MirrorMap): Pos {
        val cubeBoundary = mirrorMap.cubes
            .filter { it.x == rockPos.x && it.y > rockPos.y }
            .minOfOrNull { it.y - 1 } ?: (mirrorMap.maxY)
        val boundary = min(mirrorMap.maxY, cubeBoundary)
        val otherRocks = mirrorMap.rocks.count { it.x == rockPos.x && it.y > rockPos.y && it.y <= boundary }
        return Pos(rockPos.x, boundary - otherRocks)
    }

    private fun parseMap(input: String): MirrorMap {
        val lines = input.trim().lines().reversed()
        val maxY = lines.size
        val maxX = lines.first().length
        val positions = lines.flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                if (c in "O#") {
                    c to Pos(x + 1, y + 1)
                } else null
            }
        }
        val (rocks, cubes) = positions.partition { it.first == 'O' }
        return MirrorMap(maxX, maxY, rocks.map { it.second }.toSet(), cubes.map { it.second }.toSet())
    }

    private fun printMap(mirrorMap: MirrorMap, label: String = "") {
        if (label.isNotBlank()) println("$label:")
        (mirrorMap.maxY downTo 1).forEach { y ->
            (1..mirrorMap.maxX).forEach { x ->
                val pos = Pos(x, y)
                when (pos) {
                    in mirrorMap.rocks -> {
                        print('O')
                    }
                    in mirrorMap.cubes -> {
                        print('#')
                    }
                    else -> {
                        print('.')
                    }
                }
            }
            print("\n")
        }
        println("\n")
    }
}
