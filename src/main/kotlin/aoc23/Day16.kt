package aoc23

import io.vavr.kotlin.hashSet
import io.vavr.kotlin.toVavrSet
import io.vavr.collection.Set as VavrSet

object Day16 {

    enum class Direction {
        UP, LEFT, DOWN, RIGHT
    }

    data class Pos(val x: Int, val y: Int) {
        fun newPos(dir: Direction): Pos =
            when (dir) {
                Direction.UP -> Pos(x, y - 1)
                Direction.LEFT -> Pos(x - 1, y)
                Direction.RIGHT -> Pos(x + 1, y)
                Direction.DOWN -> Pos(x, y + 1)
            }

        fun inGrid(maxX: Int, maxY: Int) = x in 0..maxX && y in 0..maxY
    }

    data class Beam(val pos: Pos, val dir: Direction) {
        private fun newBeamAt(direction: Direction) = Beam(pos.newPos(direction), direction)
        fun newBeamAt(mirror: Char?): Pair<Beam, Beam?> =
            when (mirror) {
                '/' -> {
                    when (dir) {
                        Direction.UP -> newBeamAt(Direction.RIGHT) to null
                        Direction.LEFT -> newBeamAt(Direction.DOWN) to null
                        Direction.DOWN -> newBeamAt(Direction.LEFT) to null
                        Direction.RIGHT -> newBeamAt(Direction.UP) to null
                    }
                }

                '\\' -> {
                    when (dir) {
                        Direction.UP -> newBeamAt(Direction.LEFT) to null
                        Direction.LEFT -> newBeamAt(Direction.UP) to null
                        Direction.DOWN -> newBeamAt(Direction.RIGHT) to null
                        Direction.RIGHT -> newBeamAt(Direction.DOWN) to null
                    }
                }

                '|' -> {
                    when (dir) {
                        Direction.LEFT, Direction.RIGHT -> newBeamAt(Direction.UP) to newBeamAt(Direction.DOWN)
                        Direction.DOWN, Direction.UP -> newBeamAt(dir) to null
                    }
                }

                '-' -> {
                    when (dir) {
                        Direction.LEFT, Direction.RIGHT -> newBeamAt(dir) to null
                        Direction.DOWN, Direction.UP -> newBeamAt(Direction.LEFT) to newBeamAt(Direction.RIGHT)
                    }
                }

                else -> newBeamAt(dir) to null
            }
    }

    data class Cavern(
        val maxX: Int,
        val maxY: Int,
        val mirrors: Map<Pos, Char>,
        val beams: VavrSet<Beam> = hashSet(Beam(Pos(0, 0), Direction.RIGHT)),
        val beamsVisited: VavrSet<Beam> = beams
    ) {
        fun next(): Cavern {
            val newBeans = beams
                .flatMap { beam ->
                    beam.newBeamAt(mirrors[beam.pos]).toList()
                }
                .filterNotNull()
            val newBeamsInCavern = newBeans.filter { it.pos.inGrid(maxX, maxY) && it !in beamsVisited }.toSet().toVavrSet()
            return Cavern(maxX, maxY, mirrors, newBeamsInCavern, beamsVisited.addAll(newBeamsInCavern))
        }
    }

    fun calculateEnergizedTiles(input: String): Int {
        val cavern = parseCavern(input)
        val cavernSeq = generateSequence(cavern) { it.next() }
        val stableConfiguration = cavernSeq.dropWhile { it.beams.nonEmpty() }.first()
        return stableConfiguration.beamsVisited.map { it.pos }.size()
    }

    fun calculateMaxEnergizedTiles(input: String): Int {
        val cavern = parseCavern(input)
        val startBeans =
            (0..cavern.maxX).flatMap { x ->
                listOf(
                    Beam(Pos(x, 0), Direction.DOWN),
                    Beam(Pos(x, cavern.maxY), Direction.UP)
                )
            } +
                    (0..cavern.maxY).flatMap { y ->
                        listOf(
                            Beam(Pos(0, y), Direction.RIGHT),
                            Beam(Pos(cavern.maxX, y), Direction.LEFT)
                        )
                    }
        val startCaverns = startBeans.map { Cavern(cavern.maxX, cavern.maxY, cavern.mirrors, hashSet(it)) }
        val cavernSeq = generateSequence(startCaverns) { it.map { cavern -> cavern.next() } }
        val stableConfiguration = cavernSeq.dropWhile { cavernList -> cavernList.any { it.beams.nonEmpty() } }.first()
        val energized = stableConfiguration.map { cav ->
            cav.beamsVisited.map { b -> b.pos }.size()
        }
        return energized.max()
    }

    private fun parseCavern(input: String): Cavern {
        val lines = input.trim().lines()
        val maxX = lines.first().length - 1
        val maxY = lines.size - 1
        val mirrors = lines.flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                if (c in """/\-|""") {
                    Pos(x, y) to c
                } else null
            }
        }
        return Cavern(maxX, maxY, mirrors.toMap())
    }
}
