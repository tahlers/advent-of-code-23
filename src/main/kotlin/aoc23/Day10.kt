package aoc23

import aoc23.Day10.PipeType.HORIZONTAL
import aoc23.Day10.PipeType.NORTH_EAST
import aoc23.Day10.PipeType.NORTH_WEST
import aoc23.Day10.PipeType.SOUTH_EAST
import aoc23.Day10.PipeType.SOUTH_WEST
import aoc23.Day10.PipeType.START
import aoc23.Day10.PipeType.VERTICAL


object Day10 {

    enum class PipeType {
        VERTICAL, HORIZONTAL, NORTH_EAST, NORTH_WEST, SOUTH_WEST, SOUTH_EAST, START
    }

    data class Pos(val x: Int, val y: Int) {
        fun isLeft(other: Pos) = other.x < x && other.y == this.y
    }

    data class Pipe(val type: PipeType, val pos: Pos) {

        fun nextStep(previousPipe: Pipe?, field: Field): Pipe {
            val possibilitiesOnField = (possibleConnectionPipes() - previousPipe).filter { field.pipes[it!!.pos] == it }
            return if (possibilitiesOnField.size == 1 || type == START) {
                possibilitiesOnField.first()!!
            } else {
                throw IllegalStateException("Not on Pipe Loop!")
            }
        }

        private fun possibleConnectionPipes(): Set<Pipe> {
            val northPos = Pos(pos.x, pos.y - 1)
            val southPos = Pos(pos.x, pos.y + 1)
            val westPos = Pos(pos.x - 1, pos.y)
            val eastPos = Pos(pos.x + 1, pos.y)
            val northConnections = genPipe(northPos, VERTICAL, SOUTH_WEST, SOUTH_EAST)
            val southConnections = genPipe(southPos, VERTICAL, NORTH_WEST, NORTH_EAST)
            val eastConnections = genPipe(eastPos, HORIZONTAL, NORTH_WEST, SOUTH_WEST)
            val westConnections = genPipe(westPos, HORIZONTAL, NORTH_EAST, SOUTH_EAST)
            return when (type) {
                VERTICAL -> northConnections + southConnections
                HORIZONTAL -> eastConnections + westConnections
                NORTH_EAST -> northConnections + eastConnections
                NORTH_WEST -> northConnections + westConnections
                SOUTH_WEST -> southConnections + westConnections
                SOUTH_EAST -> southConnections + eastConnections
                START -> northConnections + southConnections + eastConnections + westConnections
            }
        }

        private fun genPipe(genPos: Pos, vararg types: PipeType) =
            (types.map { Pipe(it, genPos) } + Pipe(START, genPos)).toSet()
    }

    data class Field(val startPos: Pos, val pipes: Map<Pos, Pipe>)

    private val northTypes = setOf(VERTICAL, NORTH_EAST, NORTH_WEST)

    fun calculateStepCount(input: String): Int {
        val field = parseInputToField(input)
        val startPipe = field.pipes[field.startPos]!!
        val firstStep = startPipe.nextStep(null, field)
        val path = generateSequence(startPipe to firstStep) { (prev, current) ->
            if (current.type == START) {
                null
            } else {
                current to current.nextStep(prev, field)
            }
        }
        return (path.toList().size / 2)
    }

    fun calculateEnclosedTiles(input: String): Int {
        val field = parseInputToField(input)
        val startPipe = field.pipes[field.startPos]!!
        val firstStep = startPipe.nextStep(null, field)
        val pathSet = generateSequence(startPipe to firstStep) { (prev, current) ->
            if (current.type == START) {
                null
            } else {
                current to current.nextStep(prev, field)
            }
        }.map { it.second }.associateBy { it.pos }
        val maxX = input.lines().first().length
        val maxY = input.trim().lines().size
        val innerOrNot = (0..<maxY).flatMap { y ->
            (0..<maxX).mapNotNull { x ->
                val current = Pos(x, y)
                if (current in pathSet) {
                    null
                } else {
                    val leftCount = pathSet.filter { current.isLeft(it.key) && it.value.type in northTypes }.size
                    val isInner = (leftCount % 2) == 1
                    if (isInner) current else null
                }
            }
        }
        return innerOrNot.size
    }


    private fun parseInputToField(input: String): Field {
        // prettyPrint(input)
        val lines = input.trim().lines()
        val pipes = lines.flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                when (c) {
                    '|' -> Pipe(VERTICAL, Pos(x, y))
                    '-' -> Pipe(HORIZONTAL, Pos(x, y))
                    'L' -> Pipe(NORTH_EAST, Pos(x, y))
                    'J' -> Pipe(NORTH_WEST, Pos(x, y))
                    '7' -> Pipe(SOUTH_WEST, Pos(x, y))
                    'F' -> Pipe(SOUTH_EAST, Pos(x, y))
                    'S' -> Pipe(START, Pos(x, y))
                    else -> null
                }
            }
        }
        val pipeMap = pipes.associateBy { it.pos }
        val startPos = pipeMap.values.first { it.type == START }.pos
        return Field(startPos, pipeMap)
    }

    private fun prettyPrint(input: String) {
        val transformed = input
            .replace('-', '─')
            .replace('|', '│')
            .replace('F', '┌')
            .replace('7', '┐')
            .replace('L', '└')
            .replace('J', '┘')
            .replace('.', ' ')
        println(transformed)
    }
}